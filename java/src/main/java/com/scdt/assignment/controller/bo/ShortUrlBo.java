package com.scdt.assignment.controller.bo;

import com.alibaba.fastjson.annotation.JSONField;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @title ShortUrlBo.java
 * @description
 * @author
 * @date 2022-04-15 17:10:35
 */
@Data
@Builder
@ApiModel(value = "短链接", description = "短链接响应数据对象")
public class ShortUrlBo {

    @ApiModelProperty(value = "长链接", required = true, example = "https://github.com/scdt-china/interview-assignments/tree/master/java")
    @JSONField(ordinal = 1)
    public String longurl;

    @ApiModelProperty(value = "短链接", required = true, example = "https://domain.cn/g0u2dyn5", notes = "需要更换真实域名")
    @JSONField(ordinal = 2)
    public String shorturl;
}
