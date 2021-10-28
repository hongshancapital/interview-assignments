package com.sunjinghao.shorturl.common.util;

import com.sunjinghao.shorturl.common.result.GlobalHttpResult;
import com.sunjinghao.shorturl.common.result.GlobalResultCodeEnum;

/**
 *  通用返回对象工具类
 * @author sunjinghao
 */
public class GlobalHttpResultUtil {
    /**
     * sucess 返回成功
     *
     * @param t 数据
     */
    public static <T> GlobalHttpResult<T> success(T t) {
        GlobalHttpResult<T> vo = new GlobalHttpResult<T>(t);
        vo.setCode(GlobalResultCodeEnum.REQUEST_OK.getCode());
        vo.setMessage(GlobalResultCodeEnum.REQUEST_OK.getMsg());
        return vo;
    }

    /**
     * sucess 返回成功
     */
    public static <T> GlobalHttpResult<T> success() {
        GlobalHttpResult<T> vo = new GlobalHttpResult<T>((T) null);
        vo.setCode(GlobalResultCodeEnum.REQUEST_OK.getCode());
        vo.setMessage(GlobalResultCodeEnum.REQUEST_OK.getMsg());
        return vo;
    }

    /**
     * sucess 返回成功
     */
    public static <T> GlobalHttpResult<T> success(String msg) {
        GlobalHttpResult<T> vo = new GlobalHttpResult<T>((T) null);
        vo.setCode(GlobalResultCodeEnum.REQUEST_OK.getCode());
        vo.setMessage(msg);
        return vo;
    }

    /**
     * businessSuccess 返回业务成功
     *
     * @param t   数据信息
     * @param msg 自定义msg
     */
    public static <T> GlobalHttpResult<T> businessSuccess(T t, String msg) {
        GlobalHttpResult<T> vo = new GlobalHttpResult<T>(t);
        vo.setCode(GlobalResultCodeEnum.REQUEST_OK.getCode());
        vo.setMessage(msg);
        return vo;
    }

    /**
     * error 返回错误信息
     */
    public static <T> GlobalHttpResult<T> error() {
        GlobalHttpResult<T> vo = new GlobalHttpResult<T>((T) null);
        vo.setCode(GlobalResultCodeEnum.SERVER_ERROR.getCode());
        vo.setMessage(GlobalResultCodeEnum.SERVER_ERROR.getMsg());
        return vo;
    }


    /**
     * error 返回错误信息
     */
    public static <T> GlobalHttpResult<T> error(int code, String msg) {
        GlobalHttpResult<T> vo = new GlobalHttpResult<T>((T) null);
        vo.setCode(code);
        vo.setMessage(msg);
        return vo;
    }

    /**
     * error 返回错误信息
     */
    public static <T> GlobalHttpResult<T> error(String msg) {
        GlobalHttpResult<T> vo = new GlobalHttpResult<T>((T) null);
        vo.setCode(GlobalResultCodeEnum.SERVER_ERROR.getCode());
        vo.setMessage(msg);
        return vo;
    }

    /**
     * businessError 返回业务错误信息
     *
     * @param data data
     */
    public static <T> GlobalHttpResult<T> paramError(T data) {
        GlobalHttpResult<T> vo = new GlobalHttpResult<T>(data);
        vo.setCode(GlobalResultCodeEnum.INVALID_REQUEST.getCode());
        vo.setMessage(GlobalResultCodeEnum.INVALID_REQUEST.getMsg());
        return vo;
    }

    public static <T> GlobalHttpResult<T> createResult(GlobalResultCodeEnum code) {

        return new GlobalHttpResult<T>(code);
    }

    public static <T> GlobalHttpResult<T> createResult(GlobalResultCodeEnum code, T t) {

        return new GlobalHttpResult<T>(code, t);
    }

    public static <T> GlobalHttpResult<T> createResult(Integer code, String msg) {

        return new GlobalHttpResult<T>(code, msg);
    }


}
