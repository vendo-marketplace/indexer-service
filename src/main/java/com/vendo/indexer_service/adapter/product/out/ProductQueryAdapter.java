package com.vendo.indexer_service.adapter.product.out;

import com.vendo.indexer_service.domain.product.Product;
import com.vendo.indexer_service.port.product.ProductQueryPort;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductQueryAdapter implements ProductQueryPort {

    private final ProductClient client;

    @Override
    public List<Product> getAll(@Nullable String cursor, int limit) {
        return client.getAll(cursor, limit);
    }

}
