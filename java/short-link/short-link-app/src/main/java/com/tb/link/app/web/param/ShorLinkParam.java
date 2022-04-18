package com.tb.link.app.web.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author andy.lhc
 * @date 2022/4/16 19:34
 */
@Data
@ApiModel(value = "com.tb.link.web.param.ShorLinkParam", description = "参数")
public class ShorLinkParam implements Serializable {

    /**
     * 短链接
     * 【必选】
     */
    @ApiModelProperty(value = "shortLink",required = true,dataType = "String",name = "短链接",example = "https://scdt.cn/abcd")
    private String shortLink ;

    /**
     * appKey
     * 【可选】
     */
    @ApiModelProperty(value = "appKey",required = false,dataType = "String",name = "appKey",example = "common")
    private String  appKey ;



}
