package com.d00216118.demo.util;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Yu Chen
 * @email D00216118@student.dkit.ie
 * @date 2:25 下午 2021/3/31
 **/
@Data
public class ReponseMessage {


    public static <T> T failedResult(String code, String messge) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("result_code", code);
        map.put("message", messge);
        return (T) map;
    }

    public static <T> T successResult(Object obj) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("result_code", 0);
        map.put("message", "success");
        map.put("value", obj);
        return (T) map;
    }

}
