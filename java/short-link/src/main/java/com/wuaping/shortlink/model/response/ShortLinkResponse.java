package com.wuaping.shortlink.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 短域名存储接口返回参数
 *
 * @author Aping
 * @since 2022/3/19 10:47
 */
@Getter
@ApiModel("短域名存储接口返回参数")
@AllArgsConstructor
public class ShortLinkResponse {

    @ApiModelProperty("短域名")
    private String shortLink;

}
