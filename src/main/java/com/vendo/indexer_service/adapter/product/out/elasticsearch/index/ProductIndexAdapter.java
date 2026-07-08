package com.vendo.indexer_service.adapter.product.out.elasticsearch.index;

import com.vendo.indexer_service.adapter.product.out.elasticsearch.ElasticProduct;
import com.vendo.indexer_service.port.product.index.ProductIndexPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductIndexAdapter implements ProductIndexPort {

    private final ElasticsearchOperations operations;

    @Override
    public boolean exists() {
        IndexOperations indexOps = operations.indexOps(ElasticProduct.class);
        return indexOps.exists();
    }

    @Override
    public void create() {
        IndexOperations indexOps = operations.indexOps(ElasticProduct.class);
        indexOps.create();
        indexOps.putMapping();
    }
}
