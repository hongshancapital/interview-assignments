package com.qinghaihu.shorturl.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Json返回接口")
public class JsonSimpleResult {


    @ApiModelProperty(value = "成功标志")
    private boolean success;

    @ApiModelProperty(value = "错误信息")
    private String  errMsg;

    @ApiModelProperty(value = "结果值")
    private String  result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
