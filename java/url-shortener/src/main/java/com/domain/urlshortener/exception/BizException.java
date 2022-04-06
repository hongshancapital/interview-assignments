package com.domain.urlshortener.exception;

import com.domain.urlshortener.enums.BizStatus;

/**
 * @author: rocky.hu
 * @date: 2022/4/1 1:24
 */
public class BizException extends RuntimeException {

    private BizStatus bizStatus;

    public BizException(String message) {
        super(message);
        this.bizStatus = BizStatus.FAILED;
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
        this.bizStatus = BizStatus.FAILED;
    }

    public BizException(BizStatus bizStatus) {
        super(bizStatus.getDescription());
        this.bizStatus = bizStatus;
    }

    public BizException(BizStatus bizStatus, Throwable cause) {
        super(bizStatus.getDescription(), cause);
        this.bizStatus = bizStatus;
    }

    public BizStatus getBizStatus() {
        return bizStatus;
    }

}
