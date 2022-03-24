package com.sequoiacap.shorturl.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


public class BaseResult<T> {
    @ApiModelProperty(value="返回状态码")
    private int result;
    @ApiModelProperty(value="错误信息")
    private String errorInfo;



    public BaseResult() {
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }


}
