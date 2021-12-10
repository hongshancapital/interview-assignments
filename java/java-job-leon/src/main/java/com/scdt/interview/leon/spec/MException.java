package com.scdt.interview.leon.spec;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 业务异常类，Service类只允许抛出ServiceException，可包装原始异常类
 *
 * @author leon
 * @since 2021/10/26
 */
@Getter
@Setter
@ToString
public class MException extends RuntimeException {
    public MException(Throwable cause) {
        super(cause);
    }

    public MException(String msg) {
        super(msg);
    }

    public MException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
