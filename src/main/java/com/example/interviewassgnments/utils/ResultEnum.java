/**
 * this is a test project
 */

package com.example.interviewassgnments.utils;

/**
 *
 * @Auther: maple
 * @Date: 2022/1/19 9:29
 * @Description: com.example.interviewassgnments.utils
 * @version: 1.0
 */
public enum ResultEnum {
    /* 成功状态码 */
    SUCCESS(20000,"成功"),
    /* 异常 */
    EXCEPTION_ILLEGAL_CHARACTER(901,"非法字符"),
    /* 参数错误*/
    PARAM_IS_INVALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数为空"),
    PARAM_TYPE_BIND_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(1004, "参数缺失"),
    /* 用户错误 2001-2999*/
    USER_NOTLOGGED_IN(2001, "用户未登录"),
    USER_LOGIN_ERROR(2002, "账号不存在或密码错误"),
    SYSTEM_ERROR(10000, "系统异常，请稍后重试")
    ;

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    /**
     * 获取状态码
     *
     * @return Integer
     * */
    public Integer getCode() {
        return code;
    }

    /**
     * 获取状态信息
     *
     * @return String
     * */
    public String getMessage() {
        return message;
    }
}
