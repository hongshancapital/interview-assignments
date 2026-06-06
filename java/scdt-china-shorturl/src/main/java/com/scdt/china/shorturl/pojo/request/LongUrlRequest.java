package com.scdt.china.shorturl.pojo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @Author: zhouchao
 * @Date: 2021/12/09 12:45
 * @Description:
 */
@Data
@ApiModel(description = "短域名存储接口接收对象")
public class LongUrlRequest {

    @NotNull(message = "长链接不能为空")
    @Pattern(regexp = "(ht|f)tp(s?)\\:\\/\\/[0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*(:(0-9)*)*(\\/?)([a-zA-Z0-9\\-\\.\\?\\,\\'\\/\\\\&%\\+\\$#_=]*)?", message = "url格式不正确")
    @ApiModelProperty(value = "长连接", name = "longUrl", example = "https://www.baidu.com")
    private String longUrl;
}
