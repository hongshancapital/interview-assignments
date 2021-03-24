package com.snail.shorturlservice.vo;

import com.google.common.base.MoreObjects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@ApiModel("短链接转换请求参数实体")
public class ShortenUrlReqVO {

    @ApiModelProperty("原始URL，长URL")
    @NotBlank(message = "url不能为空")
    @Length(max = 300, message = "url长度不能超过300个字符")
    @Pattern(regexp = "^(http|https):\\/\\/([^/:]+)(:\\d*)?(.*)$", message = "url格式错误")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("url", url)
                .toString();
    }
}
