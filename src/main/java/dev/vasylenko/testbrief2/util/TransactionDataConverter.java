package dev.vasylenko.testbrief2.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TransactionDataConverter implements AttributeConverter<Map<String, String>, String> {
    private ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private Logger logger = LoggerFactory.getLogger(TransactionDataConverter.class);

    @Override
    public String convertToDatabaseColumn(Map<String, String> transactionData) {
        String data = null;
        try {
            data = OBJECT_MAPPER.writeValueAsString(transactionData);
        } catch (final JsonProcessingException e) {
            logger.error("JSON writing error", e);
        }
        return data;
    }

    @Override
    public Map<String, String> convertToEntityAttribute(String data) {
        Map<String, String> transactionData = null;
        try {
            transactionData = OBJECT_MAPPER.readValue(data,
                    new TypeReference<HashMap<String, String>>() {
                    });
        } catch (final IOException e) {
            logger.error("JSON reading error", e);
        }
        return transactionData;
    }
}
