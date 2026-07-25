package com.scdt.interview.leon.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 恢复链接请求类
 *
 * @author leon
 * @since 2021/10/26
 */
@Getter
@Setter
@ApiModel("RecoverUrlRO")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecoverUrlRO {

    @ApiModelProperty(value = "短链接", required = true)
    @NotBlank(message = "短链接不能为空")
    private String shortUrl;
}
