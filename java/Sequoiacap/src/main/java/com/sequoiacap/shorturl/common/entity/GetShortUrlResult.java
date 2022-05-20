package com.sequoiacap.shorturl.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="返回对象",description="存储短域名返回")
public class GetShortUrlResult extends BaseResult{
    @ApiModelProperty(value="返回状态码")
    private int result;
    @ApiModelProperty(value="错误信息")
    private String errorInfo;

    @ApiModelProperty(value="短域名")
    private String key;

    public GetShortUrlResult(int result, String errorInfo, String key) {
        this.result = result;
        this.errorInfo = errorInfo;
        this.key = key;
    }

    public GetShortUrlResult() {
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
