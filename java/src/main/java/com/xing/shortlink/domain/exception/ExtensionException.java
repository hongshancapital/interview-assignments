package com.xing.shortlink.domain.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 业务异常
 *
 * @Author xingzhe
 * @Date 2021/7/17 22:57
 */
@NoArgsConstructor
@Data
public class ExtensionException extends RuntimeException {

    /**
     * 业务异常码
     */
    private Integer code;

    /**
     * 业务异常信息
     */
    private String message;

    /**
     * 业务异常构造函数
     *
     * @param code    返回码
     * @param message 返回信息
     */
    public ExtensionException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
