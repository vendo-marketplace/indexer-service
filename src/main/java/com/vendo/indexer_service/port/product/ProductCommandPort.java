package com.vendo.indexer_service.port.product;

import com.vendo.indexer_service.domain.product.Product;

import java.util.List;

public interface ProductCommandPort {

    void save(Product product);

    void saveAll(List<Product> products);

    void update(String id, Product product);

}
