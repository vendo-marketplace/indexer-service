package com.vendo.indexer_service.adapter.product.out;


import com.vendo.indexer_service.adapter.product.out.elasticsearch.index.ProductReindexAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@ExtendWith(MockitoExtension.class)
public class ProductReindexAdapterTest {

    @MockitoBean
    private ElasticsearchOperations operations;

    @InjectMocks
    private ProductReindexAdapter productReindexAdapter;

    @Test
    void reindex_shouldSuccessfullyReindex() {

    }

    @Test
    void reindex_shouldLockMethod_whenAlreadyInProgress() {

    }

    @Test
    void reindex_shouldUnlockMethod_whenFinished() {

    }

}
