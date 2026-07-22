package com.liujunpei.shorturl.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 刘俊佩
 * @date 2022/1/26 下午5:03
 */
@ApiModel(description = "短域名服务请求实体类")
@Data
public class RequestEntity {
    @ApiModelProperty(value = "短域名", example = "2Iz6Br")
    private String shortUrl;

    @ApiModelProperty(value = "长域名", example = "http://www.baidu.com")
    private String longUrl;


}
