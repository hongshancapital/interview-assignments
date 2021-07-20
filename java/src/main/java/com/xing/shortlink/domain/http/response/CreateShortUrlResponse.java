package com.xing.shortlink.domain.http.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 生成短链应答
 *
 * @Author xingzhe
 * @Date 2021/7/17 22:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("生成短链接应答")
public class CreateShortUrlResponse {

    /**
     * 短链接
     */
    @ApiModelProperty(value = "生成的短链接")
    private String shortUrl;
}
