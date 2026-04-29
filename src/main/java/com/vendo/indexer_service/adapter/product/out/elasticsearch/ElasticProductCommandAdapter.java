package com.vendo.indexer_service.adapter.product.out.elasticsearch;

import com.vendo.indexer_service.port.product.ProductIndexCommandPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ElasticProductCommandAdapter implements ProductIndexCommandPort {

    private final ElasticProductRepository repository;

    @Override
    public void save(Object data) {
    }
}
