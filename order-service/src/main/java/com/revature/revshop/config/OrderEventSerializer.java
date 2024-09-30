package com.revature.revshop.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.revshop.event.OrderEvent;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class OrderEventSerializer implements Serializer<OrderEvent> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, OrderEvent data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing OrderEvent", e);
        }
    }

    @Override
    public void close() {
    }
}
