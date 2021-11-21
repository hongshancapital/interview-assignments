package com.sunjinghao.shorturl.common.util;


import com.sunjinghao.shorturl.common.result.GlobalResultCodeEnum;
import com.sunjinghao.shorturl.common.result.RpcResult;

/**
 * 通用返回对象工具类
 *
 * @author sunjinghao
 */
public class RpcResultUtil {
    /**
     * sucess 返回成功
     *
     * @param t 数据
     */
    public static <T> RpcResult<T> success(T t) {
        RpcResult<T> result = new RpcResult<>();
        result.setCode(GlobalResultCodeEnum.REQUEST_OK.getCode());
        result.setMsg(GlobalResultCodeEnum.REQUEST_OK.getMsg());
        result.setData(t);
        return result;
    }


    /**
     * error 返回错误信息
     */
    public static <T> RpcResult<T> error(String msg) {
        RpcResult<T> result = new RpcResult<>();
        result.setCode(GlobalResultCodeEnum.SERVER_ERROR.getCode());
        result.setMsg(msg);
        result.setData(null);
        return result;
    }
}
