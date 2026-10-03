package com.vendo.indexer_service.adapter.product.in.controller.kafka;

import com.vendo.event_lib.product.ProductCreatedEvent;
import com.vendo.indexer_service.adapter.product.out.mapper.EventProductMapper;
import com.vendo.indexer_service.port.product.index.ProductIndexUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class ProductCreatedEventConsumer {

    @Value("${aws.base-url}")
    private String BASE_URL;

    private final EventProductMapper mapper;
    private final ProductIndexUseCase useCase;

    @KafkaListener(
            topics = "${kafka.events.product.created-event.topic}",
            groupId = "indexer-product-created-group",
            properties = {"auto.offset.reset: ${kafka.events.product.created-event.properties.auto-offset-reset}"},
            containerFactory = "${kafka.events.product.created-event.container-factory}"
    )
    void listenProductCreatedEvent(ProductCreatedEvent event) {
        log.info("Received event for product created: {}.", event);
        useCase.save(mapper.toProduct(event, BASE_URL));
    }
}
