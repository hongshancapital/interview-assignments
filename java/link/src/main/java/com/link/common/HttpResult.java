package com.link.common;

import com.link.enums.ResultInfoEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @auth zong_hai@126.com
 * @date 2022-04-16
 * @desc
 */
@ApiModel("http请求返回结果对象")
public class HttpResult<T> {

    @ApiModelProperty("操作是否成功，success表示成功，failure表示失败")
    private String result;
    @ApiModelProperty("错误码")
    private String errorCode;
    @ApiModelProperty("错误消息")
    private String msg;
    @ApiModelProperty("数据")
    private T data;

    public HttpResult() {
    }
    /**
     * 返回成功结果
     *
     * @return
     */
    public static <T> HttpResult<T> createSuccessResult(T data) {
        HttpResult<T> httpResult = new HttpResult<>();
        httpResult.setResult("success");
        httpResult.setErrorCode(ResultInfoEnum.SUCCESS.getResultCode());
        httpResult.setMsg(ResultInfoEnum.SUCCESS.getResultDesc());
        httpResult.setData(data);
        return httpResult;
    }

    public static <T> HttpResult<T> createErrResult(ResultInfoEnum resultInfo) {
        HttpResult<T> httpResult = new HttpResult<T>();
        httpResult.setResult("failure");
        httpResult.setErrorCode(resultInfo.getResultCode());
        httpResult.setMsg(resultInfo.getResultDesc());
        return httpResult;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
