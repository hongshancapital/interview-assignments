package com.zy.url.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonUtils {
    /*json转对象*/
    public static <T> T Json2Object(Object entity, Class<T> tClass) {
        if (entity instanceof String)
            return Json2Object((String) entity,tClass);
        return JSONObject.parseObject(JSON.toJSONString(entity), tClass);
    }

    public static <T> T Json2Object(String jsonStr, Class<T> tClass) {
        return JSONObject.parseObject(jsonStr, tClass);
    }

    public static String Object2Json(Object entity) {
        return JSONObject.toJSONString(entity);
    }

    public static String Object2Json(Object entity, SerializerFeature... features) {
        return JSONObject.toJSONString(entity, features);
    }
}
