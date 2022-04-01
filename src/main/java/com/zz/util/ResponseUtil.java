package com.zz.util;

import com.zz.exception.BusinessException;
import com.zz.exception.code.SystemErrorCodeEnum;
import com.zz.param.Response;

/**
 * 工具类——构建返回报文
 *
 * @author zz
 * @version 1.0
 * @date 2022/3/31
 */
public class ResponseUtil {
    public static Response buildSuccess() {
        Response response = new Response();
        response.setSuccess(true);
        return response;
    }

    public static <T> Response buildSuccess(T data) {
        Response response = new Response();
        response.setSuccess(true);
        response.setData(data);
        return response;
    }

    public static Response businessError(BusinessException businessException) {
        Response response = new Response();
        response.setCode(businessException.getCode());
        response.setMessage(businessException.getMessage());
        return response;
    }

    public static Response businessDefault() {
        Response response = new Response();
        response.setCode(SystemErrorCodeEnum.SYS_999.name());
        response.setMessage(SystemErrorCodeEnum.SYS_999.getMessage());
        return response;
    }
}
