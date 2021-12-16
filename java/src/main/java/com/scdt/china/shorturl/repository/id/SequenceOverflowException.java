package com.scdt.china.shorturl.repository.id;

/**
 * 自增长ID序列溢出异常
 *
 * @author ng-life
 */
public class SequenceOverflowException extends RuntimeException {

    public SequenceOverflowException(String message) {
        super(message, null, false, false);
    }

}
