package com.revature.revshop.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.revature.revshop.event.OrderEvent;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class OrderEventDeserializer implements Deserializer<OrderEvent> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(OrderEventDeserializer.class);

    public OrderEventDeserializer() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public OrderEvent deserialize(String topic, byte[] data) {
        try {
            String json = new String(data);
            logger.debug("Attempting to deserialize JSON: {}", json);
            return objectMapper.readValue(data, OrderEvent.class);
        } catch (Exception e) {
            logger.error("Error deserializing OrderEvent. Raw data: {}", new String(data), e);
            return null;
        }
    }

    @Override
    public void close() {
    }
}
