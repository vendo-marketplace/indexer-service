package com.vendo.indexer_service.adapter.product.out.elasticsearch.index;

import com.vendo.indexer_service.adapter.product.out.mapper.ElasticProductMapper;
import com.vendo.indexer_service.domain.product.Product;
import com.vendo.indexer_service.port.product.index.ProductReindexPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Component
@RequiredArgsConstructor
public class ProductReindexAdapter implements ProductReindexPort {

    private static final String PRODUCTS_INDEX = "products";

    private final ElasticProductMapper elasticProductMapper;
    private final ElasticsearchOperations operations;
    private final ReentrantLock reentrantLock = new ReentrantLock();

    @Override
    public void reindex(List<Product> products) {
        if (!reentrantLock.tryLock()) return;

        try {
            operations.bulkIndex(toQueries(products), IndexCoordinates.of(PRODUCTS_INDEX));
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public boolean isProcessing() {
        return reentrantLock.isLocked();
    }

    private List<IndexQuery> toQueries(List<Product> products) {
        return products.stream()
                .map(product -> new IndexQueryBuilder().withId(product.id()).withObject(elasticProductMapper.toEntity(product)).build())
                .toList();
    }
}
