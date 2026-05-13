package com.vendo.indexer_service.adapter.product.out.elasticsearch;

import com.vendo.indexer_service.adapter.product.out.mapper.ElasticProductMapper;
import com.vendo.indexer_service.domain.product.Product;
import com.vendo.indexer_service.port.product.index.ProductIndexCommandPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ElasticProductCommandAdapter implements ProductIndexCommandPort {

    private final ElasticProductMapper mapper;

    private final ElasticProductRepository repository;

    @Override
    public void save(Product product) {
        repository.save(mapper.toEntity(product));
    }

    @Override
    public void saveAll(List<Product> products) {
        repository.saveAll(mapper.toEntities(products));
    }

    @Override
    @Transactional
    public void update(String id, Product product) {
        Optional<ElasticProduct> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) {
            log.error("Product not found by id: {}.", id);
            return;
        }

        ElasticProduct entity = entityOpt.get();
        mapper.updateEntity(entity, product);
        repository.save(entity);
    }
}
