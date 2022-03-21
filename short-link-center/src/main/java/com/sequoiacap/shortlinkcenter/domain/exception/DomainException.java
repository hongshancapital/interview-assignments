package com.sequoiacap.shortlinkcenter.domain.exception;

import com.sequoiacap.shortlinkcenter.common.enums.IErrorCode;
import com.sequoiacap.shortlinkcenter.common.exception.BaseException;

/**
 * @author xiuyuan
 * @date 2022/3/17
 */
public class DomainException extends BaseException {

    public DomainException(int code, String message) {
        super(code, message);
    }

    public DomainException(int code, String message, String detailMsg) {
        super(code, message, detailMsg);
    }

    public DomainException(IErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public DomainException(IErrorCode errorCode) {
        super(errorCode.getCode(), errorCode.getMessage());
    }
}
