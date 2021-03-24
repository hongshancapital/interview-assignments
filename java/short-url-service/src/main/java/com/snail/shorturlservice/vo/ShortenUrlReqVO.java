package com.snail.shorturlservice.vo;

import com.google.common.base.MoreObjects;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ShortenUrlReqVO {

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
