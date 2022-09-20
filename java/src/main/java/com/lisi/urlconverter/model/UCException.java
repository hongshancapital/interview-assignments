package com.lisi.urlconverter.model;

import com.lisi.urlconverter.enumeration.UCErrorType;

/**
 * @description: UrlConverter异常类
 * @author: li si
 */
public class UCException extends RuntimeException {
    private UCErrorType ucErrorType;

    public UCException(UCErrorType ucErrorType) {
        this.ucErrorType = ucErrorType;
    }

    public UCErrorType getUcErrorType() {
        return ucErrorType;
    }
}
