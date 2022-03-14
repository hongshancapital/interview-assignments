/**
 * this is a test project
 */

package com.example.interviewassgnments.utils;

/**
 * 自定义异常处理类
 *
 * @Auther: maple
 * @Date: 2022/1/19 9:27
 * @Description: com.example.interviewassgnments.utils
 * @version: 1.0
 */
public class BusinessException extends RuntimeException {

    // 异常编码
    private Integer code;

    /**
     * 业务异常
     *
     * @param code Integer 异常编码
     * @param msg  String 异常信息
     */
    public BusinessException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    /**
     * 业务异常
     *
     * @param resultCode Enum类型，已定义好相关异常描述信息
     */
    public BusinessException(ResultEnum resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }

    public Integer getCode() {
        return code;
    }
}
