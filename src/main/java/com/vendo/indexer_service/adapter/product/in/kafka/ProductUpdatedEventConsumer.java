package com.vendo.indexer_service.adapter.product.in.kafka;

import com.vendo.event_lib.product.ProductUpdatedEvent;
import com.vendo.indexer_service.adapter.product.out.mapper.EventProductMapper;
import com.vendo.indexer_service.port.product.ProductIndexUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class ProductUpdatedEventConsumer {

    private final ProductIndexUseCase productIndexUseCase;

    private final EventProductMapper mapper;

    @KafkaListener(
            topics = "${kafka.events.product.updated-event.topic}",
            groupId = "${kafka.events.product.updated-event.groupId}",
            properties = {"auto.offset.reset: ${kafka.events.product.updated-event.properties.auto-offset-reset}"},
            containerFactory = "${kafka.events.product.updated-event.container-factory}"
    )
    void listenProductUpdatedEvent(ProductUpdatedEvent event) {
        log.info("Received event for product updated: {}.", event);
        productIndexUseCase.update(event.id(), mapper.toProduct(event));
    }

}
