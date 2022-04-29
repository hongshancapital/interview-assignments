package com.hszb.shorturl.model.request;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Author: xxx
 * @License: (C) Copyright 2005-2019, xxx Corporation Limited.
 * @Date: 2021/12/18 4:40 下午
 * @Version: 1.0
 * @Description:
 */

public class TransferShortUrlRequest extends BaseRequest{

    @ApiModelProperty(value = "长域名地址列表,可批量生成,最多5个", required = true)
    private List<String> longUrls;

    public List<String> getLongUrls() {
        return longUrls;
    }

    public void setLongUrls(List<String> longUrls) {
        this.longUrls = longUrls;
    }
}
