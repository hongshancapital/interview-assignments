package com.youming.sequoia.sdn.apipublic.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;

/**
 * object和json互转工具类
 */
public class JsonTransformUtils {

    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.deactivateDefaultTyping();        //不保存类型属性
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false); // 序列化空值失败时不抛异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 反序列化不存在的字段失败时不抛异常
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        objectMapper.setDateFormat(sdf);
    }

    public static String toJson(Object object) {
        String json = null;
        // 设置时间格式yyyy-MM-dd hh:mm:ss
        try {
            json = objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    public static <T> T toObject(String content, Class<T> valueType) {
        T object = null;
        try {
            object = objectMapper.readValue(content, valueType);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return object;
    }

    //特殊的时间格式化字符串反序列化
    public static <T> T toObject(String content, Class<T> valueType, SimpleDateFormat sdf) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.deactivateDefaultTyping();        //不保存类型属性
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false); // 序列化空值失败时不抛异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 反序列化不存在的字段失败时不抛异常
        objectMapper.setDateFormat(sdf);

        T object = null;
        try {
            object = objectMapper.readValue(content, valueType);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return object;
    }

    //@SuppressWarnings({ "rawtypes" })
    public static <T> T toObject(String content, TypeReference<T> valueTypeRef) {
        T object = null;
        try {
            object = objectMapper.readValue(content, valueTypeRef);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return object;
    }

    public static JsonNode toJsonNode(String content) {
        JsonNode root = null;
        try {
            root = objectMapper.readTree(content);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return root;
    }
}
