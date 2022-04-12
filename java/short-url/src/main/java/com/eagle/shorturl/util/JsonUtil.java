package com.eagle.shorturl.util;

import com.eagle.shorturl.exception.BussinessException;
import com.eagle.shorturl.result.ResultCodeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;

/**
 * @author eagle
 * @description
 */
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String getJsonString(Object obj) {
        StringWriter writer = new StringWriter();
        try {
            objectMapper.writeValue(writer, obj);
        } catch (IOException e) {
            throw new BussinessException(ResultCodeEnum.ERROR_JSON);
        }
        return writer.toString();
    }

}
