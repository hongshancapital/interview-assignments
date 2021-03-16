package com.sequoiacap.domain.common.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 创建长短链映射请求
 */
@Data
@ApiModel("创建请求")
public class CreateUrlMapRequest implements Serializable {

    @ApiModelProperty("长链接地址")
    private String longUrl;

    @ApiModelProperty("描述信息")
    private String description;
}
