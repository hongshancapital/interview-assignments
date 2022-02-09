package com.evan.sdn.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author chenyuwen
 * @date 2021/12/13
 */
@ApiModel("域名映射")
@Builder
@Data
public class DomainNameMapping {

    @ApiModelProperty("短域名")
    private String shortDomainName;

    @ApiModelProperty("长域名")
    private String longDomainName;
}
