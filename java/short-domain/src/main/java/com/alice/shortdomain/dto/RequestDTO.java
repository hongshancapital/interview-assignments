package com.alice.shortdomain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 请求封装
 *
 * @author Alice [l1360737271@qq.com]
 * @date 2021/4/13 17:40
 */
@ApiModel(value = "请求信息封装", description = "请求信息封装")
public class RequestDTO implements Serializable {

    private static final long serialVersionUID = 8747973461289233506L;
    /**
     * 请求内容
     */
    @ApiModelProperty(value = "请求内容，url e.g. :https://www.sequoiacap.com/china")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public String toString() {
        return "RequestDTO{" +
                "url='" + url + '\'' +
                '}';
    }
}
