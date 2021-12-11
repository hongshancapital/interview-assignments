package com.scdt.china.shorturl.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.MapType;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
public class JacksonUtil {
    private static JacksonUtil _instance = new JacksonUtil();
    public static ObjectMapper objectMapper = null;

    static {
        log.info("JacksonUtil init");
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 对象转换为JSON串. <br/>
     * toJson:(obj). <br/>
     *
     * @param obj
     * @return
     * @author WangLZ
     * @since JDK 1.8
     */
    public static String toJson(Object obj) {
        String rs = null;
        try {
            rs = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("obj to json error {}", e);
            return null;
        }
        return rs;
    }
}
