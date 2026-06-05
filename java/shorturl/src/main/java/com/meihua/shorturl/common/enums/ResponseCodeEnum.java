package com.meihua.shorturl.common.enums;
/**
 *  <p>
 *   response code
 *  </p>
 * @author meihua
 * @version 1.0
 * @date 2021/10/12
 */
public enum ResponseCodeEnum {

    UNKNOWN_ERROR(-1,"未知错误"),
    SUCCESS(20000,"成功"),
    DATA_IS_NULL(20001,"数据为空"),
    PARAMETER_IS_NULL(20002,"参数为空"),
    ;

    private Integer code;
    private String message;

    ResponseCodeEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public Integer getCode(){
        return code;
    }

    public String getMessage(){
        return message;
    }
}