package com.tb.link.app.web.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author andy.lhc
 * @date 2022/4/16 20:31
 */
@Data
@ApiModel(value = "com.tb.link.web.param.LongLinkParam", description = "参数")
public class LongLinkParam implements Serializable {

    /**
     * 长链接
     * 【必选】
     */
    @ApiModelProperty(value = "originLink", required = true, dataType = "String", name = "长链接", example = "https://www.github.com/test?key=2&key3=5")
    private String originLink;

    /**
     * appKey
     * 【可选】
     */
    @ApiModelProperty(value = "appKey", required = false, dataType = "String", name = "appKey", example = "common")
    private String appKey;

    /**
     * 过期时间（秒）
     * 【必选】
     */
    @ApiModelProperty(value = "expireTime", required = true, dataType = "int", name = "过期时间（秒）", example = "86400")
    private int expireTime;

    public String getOriginLink() {
        try {
            return URLDecoder.decode(this.originLink, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return this.originLink;
        }
    }
}
