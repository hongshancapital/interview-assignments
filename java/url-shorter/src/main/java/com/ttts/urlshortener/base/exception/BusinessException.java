package com.ttts.urlshortener.base.exception;

import com.ttts.urlshortener.base.model.BaseResultCodeEnums;
import lombok.Data;

/**
 * 业务异常
 */
@Data
public class BusinessException extends RuntimeException {
    private String code;
    private String msg;

    public BusinessException(BaseResultCodeEnums codeEnums, Exception e) {
        super(e);
        this.code = codeEnums.getCode();
        this.msg = codeEnums.getMsg();
    }

    public BusinessException(BaseResultCodeEnums codeEnums) {
        this.code = codeEnums.getCode();
        this.msg = codeEnums.getMsg();
    }

    public static BusinessException of(BaseResultCodeEnums codeEnums) {
        return new BusinessException(codeEnums);
    }
}
