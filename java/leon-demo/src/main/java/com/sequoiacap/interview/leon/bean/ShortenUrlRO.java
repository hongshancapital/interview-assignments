package com.sequoiacap.interview.leon.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 缩短链接请求类
 *
 * @author leon
 * @since 2021/10/26
 */
@Getter
@Setter
@ApiModel("ShortenUrlRO")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShortenUrlRO {

    @ApiModelProperty(value = "原链接", required = true)
    @NotBlank(message = "原链接不能为空")
    @Pattern(regexp ="(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]", message="请输入以https或者http开头的正确的url")
    private String oriUrl;
}
