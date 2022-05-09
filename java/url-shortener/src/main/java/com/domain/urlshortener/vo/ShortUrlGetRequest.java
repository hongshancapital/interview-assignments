package com.domain.urlshortener.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

/**
 * @author: rocky.hu
 * @date: 2022/4/1 23:05
 */
@Schema(description = "短链读取请求体")
@Getter
@Setter
public class ShortUrlGetRequest {

    @Schema(description = "短链URL")
    @NotBlank(message = "url.notBlank")
    @URL(message = "url.valid")
    private String url;

}
