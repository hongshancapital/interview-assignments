package com.xing.shortlink.domain.http.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 原始链接查询请求
 *
 * @Author xingzhe
 * @Date 2021/7/17 22:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("查询原始链接请求")
public class QueryOriginalUrlRequest {

    /**
     * 短链接
     */
    @NotBlank(message = "短链接地址不能为空")
    @Pattern(regexp = "^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\\\/])+$",
            message = "短链接地址要以http或https开头")
    @ApiModelProperty(value = "短链接", required = true)
    private String shortUrl;
}
