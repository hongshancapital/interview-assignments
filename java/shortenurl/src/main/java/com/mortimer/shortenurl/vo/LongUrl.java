package com.mortimer.shortenurl.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

@Schema(description = "请求创建短链时请求体")
@Getter
@Setter
public class LongUrl {
    @Schema(description = "长域名地址")
    @NotBlank(message = "msg.validation.url.blank")
    @URL(message = "msg.validation.url.invalid")
    private String url;
}
