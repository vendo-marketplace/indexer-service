package com.vendo.indexer_service.port.product.index;

import com.vendo.indexer_service.domain.product.Product;

public interface ProductIndexUseCase  {

    void save(Product product);

    void update(String id, Product product);

}
