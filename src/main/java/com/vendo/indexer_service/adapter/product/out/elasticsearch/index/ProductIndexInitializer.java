package com.vendo.indexer_service.adapter.product.out.elasticsearch.index;

import com.vendo.indexer_service.adapter.product.out.elasticsearch.ElasticProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.event.EventListener;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductIndexInitializer {

    private final ElasticsearchOperations operations;

    @EventListener(ApplicationRunner.class)
    public void init() {
        IndexOperations indexOps = operations.indexOps(ElasticProduct.class);

        if (!indexOps.exists()) {
            indexOps.create();
            indexOps.putMapping();
        }
    }

}
