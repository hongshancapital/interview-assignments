package com.eagle.shorturl.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author eagle
 * @description
 */
@ApiModel(value = "长链接参数")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LongUrlParam {

    @ApiModelProperty(name = "longUrl", required = true)
    @NotBlank(message = "不能为空")
    String longUrl;

}
