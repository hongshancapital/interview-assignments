package org.calmerzhang.common.exception;

import lombok.Data;

/**
 * 业务异常
 *
 * @author calmerZhang
 * @create 2022/01/14 4:46 下午
 */
@Data
public class BusinessException extends Exception {

    private int code;
    private String msg;

    public BusinessException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

}
