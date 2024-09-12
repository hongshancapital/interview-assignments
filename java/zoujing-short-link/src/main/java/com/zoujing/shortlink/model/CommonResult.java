package com.zoujing.shortlink.model;

import com.zoujing.shortlink.exception.ShortLinkResultEnum;
import io.swagger.annotations.ApiModelProperty;

public class CommonResult<T> extends ToString {
    /**
     * 请求是否成功
     */
    @ApiModelProperty("请求是否成功")
    private boolean success;

    /**
     * 结果状态code
     */
    @ApiModelProperty("结果状态code")
    private ShortLinkResultEnum resultCode;

    /**
     * 结果状态描述
     */
    @ApiModelProperty("结果状态描述")
    private String resultDesc;

    /**
     * 结果返回数据
     */
    @ApiModelProperty("结果返回数据")
    private T data;

    public CommonResult(ShortLinkResultEnum resultEnum, boolean success) {
        this.success = success;
        this.resultCode = resultEnum;
        this.resultDesc = resultEnum.getDesc();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ShortLinkResultEnum getResultCode() {
        return resultCode;
    }

    public void setResultCode(ShortLinkResultEnum resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
