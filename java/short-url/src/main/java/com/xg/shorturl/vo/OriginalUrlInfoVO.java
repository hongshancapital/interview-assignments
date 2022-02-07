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
@ApiModel("长链接信息")
@Setter
@Getter
@ToString
public class OriginalUrlInfoVO implements Serializable {

    @ApiModelProperty("原始链接")
    private String originalUrl;
}
