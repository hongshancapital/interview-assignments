package com.hszb.shorturl.model.request;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: xxx
 * @License: (C) Copyright 2005-2019, xxx Corporation Limited.
 * @Date: 2021/12/18 12:47 下午
 * @Version: 1.0
 * @Description: 请求基础类
 */

public class BaseRequest {

    @ApiModelProperty(value = "访问站点ID，授权的appId才可访问服务", required = true, example = "10000010")
    private String appId;

    @ApiModelProperty(value = "访问服务版本号，默认1.0", example = "1.0")
    private String version;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
