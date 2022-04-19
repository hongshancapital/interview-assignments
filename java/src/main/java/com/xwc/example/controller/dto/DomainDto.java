package com.xwc.example.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 类描述：域名信息传输对象
 * 作者：徐卫超 (cc)
 * 时间 2022/4/13 18:01
 */
@Data
@ApiModel(value = "Domain", description = "域名信息")
public class DomainDto {

    @ApiModelProperty("地址信息")
    @NotNull(message = "地址信息不能为空")
    private String address;

    public static DomainDto convert(String address) {
        DomainDto tar = new DomainDto();
        tar.setAddress(address);
        return tar;
    }
}
