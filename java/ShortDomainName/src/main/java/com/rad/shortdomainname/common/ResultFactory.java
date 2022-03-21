package com.rad.shortdomainname.common;

import com.rad.shortdomainname.enums.ResultCodeEnum;

/**
 * @author xukui
 * @program: ShortDomainName
 * @description: 创建Result的工厂类
 * @date 2022-03-19 15:04:52
 */
public class ResultFactory {
    public static <T> Result createSuccess(T data) {
        return new Result(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getDesc(), data);
    }

    public static <T> Result createError(ResultCodeEnum resultCodeEnum) {
        return new Result(resultCodeEnum.getCode(), resultCodeEnum.getDesc(), null);
    }
}
