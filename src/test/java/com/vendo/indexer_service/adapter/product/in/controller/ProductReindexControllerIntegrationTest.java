package com.vendo.indexer_service.adapter.product.in.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vendo.indexer_service.adapter.product.out.elasticsearch.ElasticProductRepository;
import com.vendo.indexer_service.domain.product.Product;
import com.vendo.indexer_service.port.product.ProductQueryPort;
import com.vendo.indexer_service.port.product.index.ProductReindexPort;
import com.vendo.indexer_service.test_utils.SecurityContextService;
import com.vendo.indexer_service.test_utils.builder.ProductDataBuilder;
import com.vendo.security_lib.exception.response.ExceptionResponse;
import com.vendo.user_lib.type.UserRole;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test" )
public class ProductReindexControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductReindexPort productReindexPort;
    @MockitoBean
    private ProductQueryPort productQueryPort;
    @MockitoBean
    private ElasticProductRepository repository;

    @Value("${product.reindex.batch-size}" )
    private int REINDEX_BATCH_SIZE;

    @Nested
    class ReindexTests {

        @Test
        void reindex_shouldSuccessfullyAcceptRequest_whenAdmin() throws Exception {
            Product product = ProductDataBuilder.withAllFields().build();

            when(productReindexPort.isProcessing()).thenReturn(false);
            when(productQueryPort.getAll(null, REINDEX_BATCH_SIZE)).thenReturn(List.of(product));
            when(productQueryPort.getAll(product.id(), REINDEX_BATCH_SIZE)).thenReturn(List.of());

            mockMvc.perform(post("/indices/reindex" )
                            .with(authentication(SecurityContextService.initializeAuth(UserRole.ADMIN)))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            verify(productReindexPort).isProcessing();
            verify(productQueryPort, times(2)).getAll(any(), eq(REINDEX_BATCH_SIZE));
            verify(productReindexPort).reindex(List.of(product));
        }

        @Test
        void reindex_shouldReturnUnauthorized_whenUserNotAdmin() throws Exception {
            String content = mockMvc.perform(post("/indices/reindex" )
                            .with(authentication(SecurityContextService.initializeAuth(UserRole.USER)))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden()).andReturn().getResponse().getContentAsString();

            assertThat(content).isNotNull();
            ExceptionResponse exceptionResponse = objectMapper.readValue(content, ExceptionResponse.class);

            assertThat(exceptionResponse).isNotNull();
            assertThat(exceptionResponse.getPath()).isEqualTo("/indices/reindex" );
            assertThat(exceptionResponse.getCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
            assertThat(exceptionResponse.getMessage()).isEqualTo("Resource is unreachable." );
        }

        @Test
        void reindex_shouldDoNothing_whenAlreadyInProgress() throws Exception {
            when(productReindexPort.isProcessing()).thenReturn(true);

            mockMvc.perform(post("/indices/reindex" )
                            .with(authentication(SecurityContextService.initializeAuth(UserRole.ADMIN)))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            verify(productReindexPort).isProcessing();
            verify(productReindexPort, never()).reindex(anyList());
            verify(productQueryPort, never()).getAll(anyString(), anyInt());
        }
    }
}
