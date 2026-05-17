package com.vendo.indexer_service.domain.product;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

@Builder
public record Product(
        String id,
        String title,
        String description,
        Integer quantity,
        BigDecimal price,
        String ownerId,
        String categoryId,
        List<Attribute> attributes,
        Boolean active,
        Instant createdAt
) {

    public static Product getLast(List<Product> products) {
        if (products.isEmpty()) throw new NoSuchElementException();
        return products.get(products.size() - 1);
    }

    public record Attribute(
            String id,
            List<String> values
    ) {
    }
}
