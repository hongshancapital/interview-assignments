package com.scdt.china.shorturl.pojo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: zhouchao
 * @Date: 2021/12/09 12:46
 * @Description:
 */
@Data
@ApiModel(description = "短域名存储接口接收对象")
public class LongUrlResponse {

    @ApiModelProperty(value = "短连接",name = "shortUrl",example = "https://localhost:8080/123456")
    private String shortUrl;
}
