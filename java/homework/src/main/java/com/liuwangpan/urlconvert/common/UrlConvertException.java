package com.liuwangpan.urlconvert.common;

import com.liuwangpan.urlconvert.enums.ExceptionEnum;

/**
 * @Deacription 自定义异常
 * @author wp_li
 **/
public class UrlConvertException extends CustomException {

    public UrlConvertException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getCode(), exceptionEnum.getMessage());
    }

}