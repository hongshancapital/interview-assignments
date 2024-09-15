package com.wwwang.assignment.shortenurl.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@ApiModel("获取长域名实体")
public class GetLongUrlReq {
    @ApiModelProperty("短域名")
    @NotBlank(message = "url不能为空")
    @Size(max = 8)
    @Pattern(regexp = "^[A-Za-z0-9]+$")
    private String shortUrl;
}
