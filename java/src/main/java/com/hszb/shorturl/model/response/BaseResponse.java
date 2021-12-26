package com.hszb.shorturl.model.response;

import com.hszb.shorturl.common.enums.ResponseCodeMsg;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Author: xxx
 * @License: (C) Copyright 2005-2019, xxx Corporation Limited.
 * @Date: 2021/12/18 12:53 下午
 * @Version: 1.0
 * @Description: 请求返回结构体
 */

public class BaseResponse<T>  implements Serializable {

    @ApiModelProperty(value = "返回code，200表示正常")
    private Integer resultCode = ResponseResult.SUCCESS.resultCode;

    @ApiModelProperty(value = "错误信息，resultCode非200有值")
    private String resultMsg;

    @ApiModelProperty(value = "接口返回内容")
    private T content;

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public static <T> BaseResponse<T> succeed(T t) {
        BaseResponse<T> r = new BaseResponse<T>();
        r.resultCode = ResponseResult.SUCCESS.resultCode;
        r.content = t;
        return r;
    }

    public static <T> BaseResponse<T> fail(ResponseCodeMsg responseCodeMsg) {
        return fail(responseCodeMsg.code, responseCodeMsg.value);
    }

    public static <T> BaseResponse<T> fail(Integer result, String resultMessage) {
        return fail(null, result, resultMessage);
    }

    public static <T> BaseResponse<T> fail(T t, Integer result, String resultMessage) {
        BaseResponse<T> r = new BaseResponse<>();
        r.resultCode = result;
        r.resultMsg = resultMessage;
        r.content = t;
        return r;
    }

    public static <T> BaseResponse<T> error() {
        BaseResponse<T> r = new BaseResponse<T>();
        r.resultCode = ResponseResult.ERROR.resultCode;
        r.resultMsg = ResponseResult.ERROR.resultMessage;
        return r;
    }

    public boolean checkSuccess () {
        return null != this.resultCode && this.resultCode.equals(ResponseResult.SUCCESS.resultCode);
    }

    public enum ResponseResult {

        /**
         * 系统与业务级别均正常响应
         */
        SUCCESS(200, "响应成功"),
        /**
         * 系统级别正常响应,业务级别失败
         */
        FAILURE(1, "系统开小差，请稍后再试[1]"),
        /**
         * 系统级别响应错误
         */
        ERROR(2, "系统开小差，请稍后再试[2]");

        /** 错误码 */
        public Integer resultCode;

        /** 错误描述 */
        public String resultMessage;

        ResponseResult(int aResult, String aResultMessage) {
            this.resultCode = aResult;
            this.resultMessage = aResultMessage;
        }
    }

}
