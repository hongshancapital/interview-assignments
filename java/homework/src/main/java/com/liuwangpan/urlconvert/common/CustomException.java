package com.liuwangpan.urlconvert.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Deacription CustomException
 * @author wp_li
 **/
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomException extends RuntimeException {

    /**
     * 业务异常码
     */
    private Long code;

    /**
     * 业务异常信息
     */
    private String message;


    /**
     * cause
     */
    private Throwable cause;

    /**
     * 业务域标识自动取当前服务
     *
     * @param code    code
     * @param message message
     */
    public CustomException(Long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Deprecated
    public CustomException(Long code, String message, Object object, Throwable throwable) {
        this.code = code;
        this.message = message;
        this.cause = throwable;
    }

}