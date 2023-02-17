package com.zhangliang.suril.controller.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 存储url参数
 *
 * @author zhang
 * @date 2021/12/02
 */
@Getter
@Setter
@ApiModel(value = "提交URL参数", description = "存入url的参数对象")
public class PostUrlParams {

    /**
     * 原始url
     */
    @NotNull(message = "{params.url.not-null}")
    @ApiModelProperty(value = "原始URL", name = "originalUrl", notes = "请提供正确的域名格式", example = "http://baidu.com")
    private String originalUrl;
}
