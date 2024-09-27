package com.hongshan.shorturl.model.reqs;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * @author: huachengqiang
 * @date: 2021/12/28
 * @description:
 */
@Data
@ApiModel
public class ShortUrlGenRequest {
    /**
     * 原始url(这里的正则待优化）
     */
    @ApiModelProperty("原始url")
    @Pattern(regexp = "https?://.+", message = "不是有效的url")
    private String originUrl;
    /**
     * 有效截止时间，单位：秒
     */
    @ApiModelProperty("链接有效时长")
    private Long expireAt;
    /**
     * 签名
     */
    @ApiModelProperty("url签名")
    private String sign;
}
