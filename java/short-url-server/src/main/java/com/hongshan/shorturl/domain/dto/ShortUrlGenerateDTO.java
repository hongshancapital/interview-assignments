package com.hongshan.shorturl.domain.dto;

import com.hongshan.shorturl.validator.ExpireTimeValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author: huachengqiang
 * @date: 2022/3/19
 * @description:
 */
@Data
@ApiModel
public class ShortUrlGenerateDTO {
    /**
     * 原始url(这里的正则待优化）
     */
    @ApiModelProperty("原始url")
    @NotBlank(message = "原始地址不能为空")
    @Pattern(regexp = "^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]", message = "原始链接不符合URL语法")
    private String originUrl;
    /**
     * 有效截止时间，单位：毫秒
     */
    @ApiModelProperty("链接过期时间")
    @ExpireTimeValidator.ExpireTimeConstraint
    private Long expireAt;
}
