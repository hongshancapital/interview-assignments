package com.scdt.china.shorturl.pojo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: zhouchao
 * @Date: 2021/12/09 12:33
 * @Description:
 */
@Data
@ApiModel(description = "短域名读取接口返回对象")
public class ShortUrlResponse {

    @ApiModelProperty(value = "长连接",name = "longUrl",example = "https://www.baidu.com")
    private String longUrl;
}
