package com.shortlink.common;

/**
 *
 * 返回实体构造器
 */
public class ResponseBuilder {

    public static Result buildSuccess(Object data) {
        return new Result(data);
    }

    public static Result bulidError(GeneralException exception) {
        return bulidError(exception.getCode(), exception.getDesc());
    }

    public static Result bulidError(Integer code, String desc) {
        return new Result(code, desc);
    }
}
