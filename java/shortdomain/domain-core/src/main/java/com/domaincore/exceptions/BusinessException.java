package com.domaincore.exceptions;

import com.domaincore.constants.ErrorInfoEnum;
import lombok.Data;

/**
 * 业务异常类
 *
 * @author Administrator
 * @Date 2021/9/21
 */
@Data
public class BusinessException extends RuntimeException {
    /**
     * 异常编码
     */
    private String code;

    /**
     * 附加数据
     */
    private Object data;

    /**
     * 业务异常构造函数
     *
     * @param message 消息
     */
    public BusinessException(String message) {
        super(message);
    }

    /**
     * 业务异常构造函数
     *
     * @param errorInfoEnum 异常枚举消息
     */
    public BusinessException(ErrorInfoEnum errorInfoEnum) {
        super(errorInfoEnum.getResultMsg());
        this.code = errorInfoEnum.getResultCode();
    }

    /**
     * 业务异常构造函数
     *
     * @param errorInfoEnum 异常枚举消息
     * @param cause         异常对象
     * @param data          扩展数据
     */
    public BusinessException(ErrorInfoEnum errorInfoEnum, Throwable cause, Object data) {
        super(errorInfoEnum.getResultMsg(), cause);
        this.code = errorInfoEnum.getResultCode();
        this.data = data;
    }

    /**
     * 业务异常构造函数
     *
     * @param cause 异常对象
     */
    public BusinessException(Throwable cause) {
        super(cause);
    }
}
