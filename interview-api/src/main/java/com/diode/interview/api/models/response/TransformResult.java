package com.diode.interview.api.models.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author unlikeha@163.com
 * @date 2022/4/28
 */
@Data
@ApiModel("连接转换请求体")
public class TransformResult {
    @ApiModelProperty("转换后链接")
    private String url;
}
