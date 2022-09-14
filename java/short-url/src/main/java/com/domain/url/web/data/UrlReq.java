package com.domain.url.web.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 短链接服务请求对象封装类
 */
@ApiModel("短链接服务请求对象封装类")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrlReq {
    @ApiModelProperty(value = "链接地址", required = true)
    private String url;
}
