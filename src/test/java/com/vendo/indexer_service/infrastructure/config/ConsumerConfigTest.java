package com.vendo.indexer_service.infrastructure.config;

import com.vendo.event_lib.product.ProductCreatedEvent;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.apache.kafka.common.serialization.Deserializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.support.serializer.SerializationUtils;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ConsumerConfigTest {

    private static final String TOPIC = "PRODUCT_CREATED_TEST_TOPIC";

    private Deserializer<Object> valueDeserializer;

    @BeforeEach
    void setUp() {
        ConsumerConfig consumerConfig = new ConsumerConfig();
        ReflectionTestUtils.setField(consumerConfig, "KAFKA_BOOTSTRAP_SERVERS", List.of("test"));
        valueDeserializer = consumerConfig.consumerFactory().getValueDeserializer();
    }

    @Test
    void consumerFactory_shouldDeserializeEvent_whenMessageIsValidJson() {
        ProductCreatedEvent event = ProductCreatedEvent.builder().id("product-id").title("iPhone 15").build();
        RecordHeaders headers = new RecordHeaders();
        byte[] payload;
        try (JsonSerializer<ProductCreatedEvent> serializer = new JsonSerializer<>()) {
            payload = serializer.serialize(TOPIC, headers, event);
        }

        Object result = valueDeserializer.deserialize(TOPIC, headers, payload);

        assertThat(result).isEqualTo(event);
        assertThat(headers.lastHeader(SerializationUtils.VALUE_DESERIALIZER_EXCEPTION_HEADER)).isNull();
    }

    @Test
    void consumerFactory_shouldReturnNullAndSetExceptionHeader_whenMessageIsNotJson() {
        RecordHeaders headers = new RecordHeaders();
        byte[] payload = "categories/491aab8d-d71a-442e-a235-589ca95d2c76.jpg".getBytes(StandardCharsets.UTF_8);

        Object result = valueDeserializer.deserialize(TOPIC, headers, payload);

        assertThat(result).isNull();
        assertThat(headers.lastHeader(SerializationUtils.VALUE_DESERIALIZER_EXCEPTION_HEADER)).isNotNull();
    }
}
