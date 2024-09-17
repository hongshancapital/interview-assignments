package com.jingdata.params.write;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 短地址写入请求参数
 *
 * @Author wuyl
 * @Date 2022/4/6 23:08:02
 */
@Data
@ApiModel
public class ShortUrlWriteRequest {

    @ApiModelProperty(value = "长地址", required = true)
    private String longUrl;

}
