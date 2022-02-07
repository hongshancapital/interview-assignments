package com.xg.shorturl.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author xionggen
 */
@ApiModel("短链接信息")
@Setter
@Getter
@ToString
public class ShortUrlInfoVO implements Serializable {

    @ApiModelProperty("短链接")
    private String shortUrl;
}
