package com.scdt.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 张来刚
 * 2021/10/9 0009.
 */
@Data
@ApiModel("链接转换返回基本体")
public class BaseUrlResult {
    @ApiModelProperty("转换的目标链接")
    String url;

    public BaseUrlResult(String url){
        this.url = url;
    }
}
