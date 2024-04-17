package com.scdt.china.shorturl.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 长域名请求
 *
 * @author：costa
 * @date：Created in 2022/4/11 18:34
 */
@ApiModel("长域名请求")
@Setter
@Getter
public class LongUrlReq {

    @ApiModelProperty("长域名")
    @NotBlank(message = "URL不能为空")
    private String url;

}
