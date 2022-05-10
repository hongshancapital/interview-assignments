package com.jingdata.params.read;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 短地址读取请求参数
 *
 * @Author wuyl
 * @Date 2022/4/6 23:08:02
 */
@Data
@ApiModel
public class ShortUrlReadRequest {

    @ApiModelProperty(value = "短地址", required = true)
    private String shortUrl;

}
