package com.utils;

import java.util.HashMap;
import java.util.Map;

public class Constant {

    public static  final String charset = "UTF-8";

    /**
     * response return obj
     */
    public static Integer RESULT_OBJ_STATUS_SUCCESS = 1;    // 成功
    public static String RESULT_OBJ_STATUS_SUCCESS_MSG = "成功"; // 成功

    public static String RESULT_OBJ_STATUS_SUCCESS_NOTFIND_MSG = "短连接无效"; // 成功

    public static Integer RESULT_OBJ_STATUS_FAIL = 2;   // 失败
    public static String RESULT_OBJ_STATUS_FAIL_MSG = "失败";   // 失败

    /**
     * 异常
     */
    public static String RESULT_OBJ_EXCEPTION_MSG = "服务异常";

    public static String RESULT_OBJ_NULLPORINTEXCEPTION_MSG = "服务空指针异常";


    /**
     * 短链接url
     */
    public static Map<String,String> URL_SHORTURL = new HashMap<String,String>();
}
