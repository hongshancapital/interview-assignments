package com.homework.shorturl.exception;

import lombok.Data;

@Data
public class ShortUrlBusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public String code;
    public String rawMessage;

    public ShortUrlBusinessException() {
        super();
    }

    public ShortUrlBusinessException(String msg) {
        super(msg);
    }

    public ShortUrlBusinessException(ExceptionCode code, String msg) {
        super(msg);
        this.code = code.name();
    }
}
