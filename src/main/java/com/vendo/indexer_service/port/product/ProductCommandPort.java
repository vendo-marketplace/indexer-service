package com.vendo.indexer_service.port.product;

import com.vendo.indexer_service.domain.product.Product;

public interface ProductCommandPort {

    void save(Product product);

    void update(String id, Product product);

}
