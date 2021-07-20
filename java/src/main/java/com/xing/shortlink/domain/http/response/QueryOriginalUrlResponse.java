package com.xing.shortlink.domain.http.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 原始链接查询应答
 *
 * @Author xingzhe
 * @Date 2021/7/17 22:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("查询原始链接应答")
public class QueryOriginalUrlResponse {

    /**
     * 原始链接
     */
    @ApiModelProperty(value = "原始链接")
    private String originalUrl;
}
