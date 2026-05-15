package com.vendo.indexer_service.adapter.product.in.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vendo.indexer_service.adapter.product.out.elasticsearch.ElasticProductRepository;
import com.vendo.indexer_service.adapter.security.out.jwt.parser.TokenClaims;
import com.vendo.indexer_service.config.TestAsyncConfig;
import com.vendo.indexer_service.port.product.ProductQueryPort;
import com.vendo.indexer_service.port.product.index.ProductReindexPort;
import com.vendo.indexer_service.port.product.index.ProductReindexUseCase;
import com.vendo.indexer_service.test_utils.SecurityContextService;
import com.vendo.user_lib.type.UserRole;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductReindexControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ElasticsearchOperations elasticsearchOperations;
    @MockitoBean
    private ElasticProductRepository elasticProductRepository;

    @MockitoBean
    private ProductReindexUseCase productReindexUseCase;
    @MockitoBean
    private ProductReindexPort productReindexPort;
    @MockitoBean
    private ProductQueryPort productQueryPort;

    @Value("${product.reindex.batch-size}")
    private int BATCH_SIZE;

    @Nested
    @Import(TestAsyncConfig.class)
    class ReindexTests {

        @Test
        void reindex_shouldSuccessfullyReindexProducts() throws Exception {
            when(productReindexPort.isProcessing()).thenReturn(false);
            when(productQueryPort.getAll(null, BATCH_SIZE)).thenReturn(List.of());

            mockMvc.perform(post("/products/reindex")
                            .with(authentication(SecurityContextService.initializeAuth(new TokenClaims("id", List.of(UserRole.ADMIN.name())))))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            verify(productReindexPort).isProcessing();
            verify(productQueryPort).getAll(null, BATCH_SIZE);
        }

        @Test
        void reindex_shouldReturnUnauthorized_whenUserNotAdmin() {

        }

        @Test
        void reindex_shouldReturnConflict_whenReindexingInProgress() {

        }

        @Test
        void reindex_shouldFinishReindexing_whenNoProducts() {

        }

    }
}
