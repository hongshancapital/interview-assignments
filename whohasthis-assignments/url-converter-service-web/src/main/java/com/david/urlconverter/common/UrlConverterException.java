package com.david.urlconverter.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UrlConverterException extends RuntimeException{
    protected String code;
    protected String msg;
    protected ResultStatusCode statusCode;

    public UrlConverterException(ResultStatusCode statusCode){
        super(statusCode.msg());
        this.statusCode = statusCode;
        this.code = statusCode.code();
        this.msg = statusCode.msg();

    }
}
