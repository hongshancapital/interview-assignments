package com.zz.param.outparam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 短链接的返回结果
 *
 * @author zz
 * @version 1.0
 * @date 2022/3/31
 */
@ApiModel(description = "返回值")
public class ShortUrlDTO implements Serializable {
    private static final long serialVersionUID = -2028552486729580210L;
    /**
     * 短链接生成的编码
     */
    @ApiModelProperty(name = "shortCode", value = "短链接编码")
    private String shortCode;
    /**
     * 映射的原始的url
     */
    @ApiModelProperty(name = "originHttpUrl",value = "原始url")
    private String originHttpUrl;

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getOriginHttpUrl() {
        return originHttpUrl;
    }

    public void setOriginHttpUrl(String originHttpUrl) {
        this.originHttpUrl = originHttpUrl;
    }
}
