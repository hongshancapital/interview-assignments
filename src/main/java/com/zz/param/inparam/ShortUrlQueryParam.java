package com.zz.param.inparam;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 短链接获取请求参数
 *
 * @author zz
 * @version 1.0
 * @date 2022/3/31
 */
@ApiModel(description = "短链接获取参数")
public class ShortUrlQueryParam implements Serializable {
    private static final long serialVersionUID = 896119766154287325L;
    /**
     * 短域名的code
     */
    @ApiModelProperty(name = "shortCode", value = "短链接编码", required = true)
    @NotNull(message = "短码不能为空")
    private String shortCode;

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }
}
