package com.vendo.indexer_service.adapter.product.out.elasticsearch.index;

import com.vendo.indexer_service.adapter.product.out.elasticsearch.ElasticProduct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductIndexInitializer {

    private final ElasticsearchOperations operations;

    @EventListener(ApplicationStartedEvent.class)
    public void init() {
        log.info("Class started.");
        IndexOperations indexOps = operations.indexOps(ElasticProduct.class);

        log.info(indexOps.toString());
        log.info(String.valueOf(indexOps.exists()));

        if (!indexOps.exists()) {
            log.info("Initializing product index.");
            indexOps.create();
            indexOps.putMapping();
        }
    }

}
