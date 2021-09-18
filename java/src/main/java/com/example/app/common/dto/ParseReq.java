package com.example.app.common.dto;

import com.example.app.common.constants.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

/**
 * 短域名生成 Req
 *
 * @author voidm
 * @date 2021/9/18
 */
@ApiModel("短域名请求类")
public class ParseReq {

    @Pattern(regexp = Constants.REGEX_URL,message = "短域名 格式错误")
    @Length(max = Constants.URL_MAX_LENGTH, message = "超过域名最大长度 [" + Constants.URL_MAX_LENGTH + "]")
    @ApiModelProperty("短域名")
    private String shortUrl;

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}