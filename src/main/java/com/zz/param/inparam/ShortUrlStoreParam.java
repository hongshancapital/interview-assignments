package com.zz.param.inparam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 短链接存储请求参数
 *
 * @author zz
 * @version 1.0
 * @date 2022/3/31
 */
@ApiModel(description = "短链接存储参数")
public class ShortUrlStoreParam {
    /**
     * 具体的http的url，形式如下http:// https://
     */
    @ApiModelProperty(name = "httpUrl", value = "长域名", required = true)
    @NotNull(message = "长域名不能是空")
    @Pattern(regexp = "^(http|https)://[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?$", message = "长域名的格式不对")
    private String httpUrl;

    public String getHttpUrl() {
        return httpUrl;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }
}
