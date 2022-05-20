package com.sequoiacap.shorturl.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="返回对象",description="读取域名返回")
public class GetUrlResult extends BaseResult {
    @ApiModelProperty(value="返回状态码")
    private int result;
    @ApiModelProperty(value="错误信息")
    private String errorInfo;

    @ApiModelProperty(value="长域名")
    private String url;

    public GetUrlResult(int result, String errorInfo, String url) {
        this.result = result;
        this.errorInfo = errorInfo;
        this.url = url;
    }

    public GetUrlResult() {
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


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
