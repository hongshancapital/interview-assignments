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
@ApiModel("长域名请求类")
public class GenerateReq {

    @Pattern(regexp = Constants.REGEX_URL, message = "长域名 格式错误")
    @Length(max = Constants.URL_MAX_LENGTH, message = "超过域名最大长度 [" + Constants.URL_MAX_LENGTH + "]")
    @ApiModelProperty("长域名")
    private String fullUrl;

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }
}