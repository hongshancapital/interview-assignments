package com.domain.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author ：ji
 * @description：
 */
@ApiModel("短域名存储接口请求对象封装类")
public class ShortDomainRequestBean {
    @ApiModelProperty("长域名URL")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
