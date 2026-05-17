package com.vendo.indexer_service.infrastructure.products;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductReindexBatchConfig {

    @Value("${product.reindex.batch-size}")
    private int BATCH_SIZE;

    @Bean
    public int reindexBatchSize() {
        return BATCH_SIZE;
    }

}
