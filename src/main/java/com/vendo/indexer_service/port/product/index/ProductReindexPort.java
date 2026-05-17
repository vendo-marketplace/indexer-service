package com.vendo.indexer_service.port.product.index;

import com.vendo.indexer_service.domain.product.Product;

import java.util.List;

public interface ProductReindexPort {

    void reindex(List<Product> products);

    boolean isProcessing();

}
