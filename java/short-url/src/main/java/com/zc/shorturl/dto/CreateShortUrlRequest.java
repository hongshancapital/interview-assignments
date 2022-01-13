package com.zc.shorturl.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.URL;

@ApiModel(description = "创建短链接的请求对象")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateShortUrlRequest {
    @ApiModelProperty(required = true, notes = "Url to convert to short")
    @JsonProperty("long_url")
    // regexp还需要充分测试
    @URL(regexp = "((http|https)://)[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)",
            message = "无效的url")
    private String longUrl;
}
