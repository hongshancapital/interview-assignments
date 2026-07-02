package com.wuaping.shortlink.exception;

import lombok.Getter;

/**
 * 错误码和错误信息
 *
 * @author Aping
 * @since 2022/3/19 14:38
 */
public class ExceptionCodeMessage {

    /**
     * 参数非法
     */
    public static final ExceptionCodeMessage.CodeMessage INVALID_PARAM = new CodeMessage(3001, "invalid parameter");


    /**
     * 业务失败
     */
    public static final ExceptionCodeMessage.CodeMessage SERVICE_ERROR = new CodeMessage(5001, "service failed");

    /**
     * 短域名已过期
     */
    public static final ExceptionCodeMessage.CodeMessage LINK_EXPIRED = new CodeMessage(5002, "link expired");

    @Getter
    public static class CodeMessage {
        private final int code;

        private final String message;

        public CodeMessage(int code, String message) {
            this.code = code;
            this.message = message;
        }
    }

}
