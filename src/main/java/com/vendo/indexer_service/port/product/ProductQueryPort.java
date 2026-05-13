package com.vendo.indexer_service.port.product;

import com.vendo.indexer_service.domain.product.Product;
import jakarta.annotation.Nullable;

import java.util.List;

public interface ProductQueryPort {

    List<Product> getAll(@Nullable String cursor, int limit);

}
