package com.vendo.indexer_service.port.product;

import com.vendo.indexer_service.domain.product.Product;

public interface ProductIndexCommandPort {

    void save(Product product);

}
