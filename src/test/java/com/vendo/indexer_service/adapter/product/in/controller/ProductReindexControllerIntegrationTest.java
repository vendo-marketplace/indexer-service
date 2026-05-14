package com.vendo.indexer_service.adapter.product.in.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vendo.indexer_service.adapter.security.out.jwt.parser.TokenClaims;
import com.vendo.indexer_service.config.TestAsyncConfig;
import com.vendo.indexer_service.test_utils.SecurityContextService;
import com.vendo.user_lib.type.UserRole;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

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

    @Nested
    @Import(TestAsyncConfig.class)
    class ReindexTests {

        @Test
        void reindex_shouldSuccessfullyReindexProducts() throws Exception {
            SecurityContext adminContext = SecurityContextService.initializeSecurityContext(new TokenClaims("id", List.of(UserRole.ADMIN.name())));

            mockMvc.perform(post("/products/reindex")
                            .contentType(MediaType.APPLICATION_JSON)
                            .with(SecurityMockMvcRequestPostProcessors.securityContext(adminContext)))
                    .andExpect(status().isOk());
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
