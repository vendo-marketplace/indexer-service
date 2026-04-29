package com.vendo.indexer_service.adapter.product.in.kafka.dto;

import java.math.BigDecimal;
import java.util.List;

// TODO move to event-lib
public record ProductCreatedEvent(
        String id,
        String title,
        String description,
        Integer quantity,
        BigDecimal price,
        String ownerId,
        String categoryId,
        List<Attribute> attributes,
        Boolean active
) {

    public record Attribute(
            String title,
            // TODO move Attribute type to lib to use here
            String type,
            List<String> values
    ) {
    }
}
