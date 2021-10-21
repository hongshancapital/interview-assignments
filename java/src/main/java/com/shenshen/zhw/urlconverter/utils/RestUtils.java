package com.shenshen.zhw.urlconverter.utils;

import com.alibaba.fastjson.JSONObject;


/**
 * 说明：返回restful数据标准接口
 *
 * @author zhangwei
 * @date 2021/11/4 11:46
 */
public class RestUtils {
    private static final String RESULT_STR = "{\"success\":\"true\",\"msg\":\"\",\"data\":{},\"timeout\":false,\"time\":-1}";

    private static JSONObject createJSON() {

        JSONObject jsonObject = JSONObject.parseObject(RESULT_STR);
        jsonObject.put("time", System.currentTimeMillis());
        return jsonObject;
    }

    public static JSONObject successJSON(String msg) {

        JSONObject jsonObject = createJSON();
        jsonObject.put("msg", msg);
        return jsonObject;
    }

    public static JSONObject successJSON(String msg, Object object) {

        JSONObject jsonObject = createJSON();
        jsonObject.put("msg", msg);
        jsonObject.put("data", object);
        return jsonObject;
    }


    public static String success(String msg, Object object) {

        return successJSON(msg, object).toJSONString();
    }


    public static JSONObject errorJSON(String msg) {

        JSONObject jsonObject = createJSON();
        jsonObject.put("msg", msg);
        jsonObject.put("success", "false");
        return jsonObject;
    }

    public static JSONObject errorJSON(int errorCode, String msg) {

        JSONObject jsonObject = errorJSON(msg);
        jsonObject.put("errorCode", errorCode);
        return jsonObject;
    }


    public static String error(int errorCode, String msg) {

        return errorJSON(errorCode, msg).toString();
    }

}
