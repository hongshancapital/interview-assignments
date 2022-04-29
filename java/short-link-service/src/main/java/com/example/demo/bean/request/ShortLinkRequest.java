package com.example.demo.bean.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * @author shenbing
 * @since 2022/1/8
 */
public class ShortLinkRequest {

    @ApiModelProperty("原始链接")
    @NotBlank(message = "原始链接不能为空")
    private String originalUrl;

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

}
