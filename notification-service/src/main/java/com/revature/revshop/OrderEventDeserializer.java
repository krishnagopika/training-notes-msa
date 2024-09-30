package com.revature.revshop.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.revshop.event.OrderEvent;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class OrderEventDeserializer implements Deserializer<OrderEvent> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public OrderEvent deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, OrderEvent.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing OrderEvent", e);
        }
    }

    @Override
    public void close() {
    }
}
