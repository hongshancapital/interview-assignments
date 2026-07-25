package com.scdt.interview.leon.spec;

/**
 * 常量类
 *
 * @author leon
 * @since 2021/10/26
 */
public class MConstants {
    /** 接口返回代码 **/
    public static String RTN_CODE_SUCCESS = "200";              //成功
    public static String RTN_CODE_ERROR = "500";                //失败
    public static String RTN_CODE_BADREQUEST = "400";           //错误请求（参数校验错误、格式错误或者数据不完整）

    /** 短链接最大长度 **/
    public static Integer URL_LENGTH_MAX = 8;
}
