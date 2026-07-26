package com.vendo.indexer_service.adapter.product.out.elasticsearch;

import com.vendo.indexer_service.adapter.product.out.mapper.ElasticProductMapper;
import com.vendo.indexer_service.domain.product.Product;
import com.vendo.indexer_service.port.product.ProductCommandPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ElasticProductCommandAdapter implements ProductCommandPort {

    @Value("${aws.base-url}")
    private String BASE_URL;

    private final ElasticProductMapper mapper;
    private final ElasticProductRepository repository;

    @Override
    public void save(Product product) {
        repository.save(mapper.toEntity(product));
    }

    @Override
    public void update(String id, Product product) {
        Optional<ElasticProduct> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) {
            log.error("Product not found by id: {}.", id);
            return;
        }

        ElasticProduct entity = entityOpt.get();
        mapper.updateEntity(entity, product, BASE_URL);
        repository.save(entity);
    }
}
