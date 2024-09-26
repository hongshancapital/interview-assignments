package com.hszb.shorturl.model.request;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: xxx
 * @License: (C) Copyright 2005-2019, xxx Corporation Limited.
 * @Date: 2021/12/20 8:01 下午
 * @Version: 1.0
 * @Description:
 */

public class QueryLongUrlRequest extends BaseRequest {

    @ApiModelProperty(value = "短域名code", required = true, example = "XC8i")
    private String shortCode;

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }
}
