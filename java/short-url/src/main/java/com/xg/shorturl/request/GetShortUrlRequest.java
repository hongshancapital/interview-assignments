package com.xg.shorturl.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author xionggen
 */
@ApiModel("获取短链接")
@Setter
@Getter
@ToString
public class GetShortUrlRequest implements Serializable {

    @ApiModelProperty("原始链接")
    @NotBlank(message = "originalUrl不能为空")
    private String originalUrl;
}
