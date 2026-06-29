package com.jingdata.params.write;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 短地址写入响应参数
 *
 * @Author wuyl
 * @Date 2022/4/6 23:08:02
 */
@Data
@AllArgsConstructor
@ApiModel
public class ShortUrlWriteResponse {

    @ApiModelProperty(value = "短地址", required = true)
    private String shortUrl;

}
