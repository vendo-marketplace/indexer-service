package com.vendo.indexer_service.test_utils.builder;

import com.vendo.indexer_service.domain.product.Product;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class ProductDataBuilder {

    public static Product.ProductBuilder withAllFields() {
        Product.Attribute attribute = new Product.Attribute(String.valueOf(UUID.randomUUID()), List.of("value"));

        return Product.builder()
                .id(String.valueOf(UUID.randomUUID()))
                .title("title")
                .description("description")
                .price(BigDecimal.ONE)
                .quantity(1)
                .active(true)
                .categoryId(String.valueOf(UUID.randomUUID()))
                .ownerId(String.valueOf(UUID.randomUUID()))
                .attributes(List.of(attribute))
                .createdAt(Instant.now());
    }

}
