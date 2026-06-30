package com.domain.url.service.codes;

import com.domain.url.exception.IExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * UrlService错误枚举类
 */
@Getter
@AllArgsConstructor
public enum UrlServiceExceptionCode implements IExceptionCode {
    __URL_E_0001__("无效的长链接"),
    __URL_E_0002__("无效的短链接"),
    __URL_E_0003__("长链接不存在"),
    ;

    private final String message;
}
