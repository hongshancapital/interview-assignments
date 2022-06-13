package com.sequoia.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * Descript:
 * File: com.sequoia.domain.UrlRequest
 * Author: daishengkai
 * Date: 2022/3/30
 * Copyright (c) 2022,All Rights Reserved.
 */
@ToString
@ApiModel("长短链接请求实体")
public class UrlRequest {

    public UrlRequest() {}

    public UrlRequest(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    @ApiModelProperty("链接url")
    private String url;

}
