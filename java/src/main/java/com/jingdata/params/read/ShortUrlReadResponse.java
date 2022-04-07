package com.jingdata.params.read;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 短地址读取响应参数
 *
 * @Author wuyl
 * @Date 2022/4/6 23:08:02
 */
@Data
@AllArgsConstructor
@ApiModel
public class ShortUrlReadResponse {

    @ApiModelProperty(value = "长地址", required = true)
    private String longUrl;

}
