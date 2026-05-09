package com.vendo.indexer_service.adapter.product.in.kafka;

import com.vendo.event_lib.product.ProductCreatedEvent;
import com.vendo.indexer_service.adapter.product.out.mapper.EventProductMapper;
import com.vendo.indexer_service.port.product.ProductIndexUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class ProductCreatedEventConsumer {

    private final ProductIndexUseCase productIndexUseCase;

    private final EventProductMapper mapper;

    @KafkaListener(
            topics = "${kafka.events.product.created-event.topic}",
            groupId = "${kafka.events.product.created-event.groupId}",
            properties = {"auto.offset.reset: ${kafka.events.product.created-event.properties.auto-offset-reset}"},
            containerFactory = "${kafka.events.product.created-event.container-factory}"
    )
    void listenProductCreatedEvent(ProductCreatedEvent event) {
        log.info("Received event for product created: {}.", event);
        productIndexUseCase.save(mapper.toProduct(event));
    }
}
