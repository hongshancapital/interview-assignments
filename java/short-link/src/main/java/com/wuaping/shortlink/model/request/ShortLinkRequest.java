package com.wuaping.shortlink.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * 短域名存储接口请求参数
 *
 * @author Aping
 * @since 2022/3/19 10:47
 */
@Getter
@Setter
@ApiModel("短域名存储接口请求参数")
@ToString
public class ShortLinkRequest {

    @ApiModelProperty("原域名")
    @NotBlank
    private String originalLink;

}
