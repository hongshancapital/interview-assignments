package com.sequoiacap.domain.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sequoiacap.domain.exception.JsonException;

/**
 * json工具类
 */
public enum JacksonUtils {

    /**
     * 单例
     */
    X;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    public String format(Object content) {
        try {
            return MAPPER.writeValueAsString(content);
        } catch (Exception e) {
            throw new JsonException(e);
        }
    }

    public <T> T parse(String content, Class<T> targetClass) {
        try {
            return MAPPER.readValue(content, targetClass);
        } catch (Exception e) {
            throw new JsonException(e);
        }
    }
}
