package com.scdt.url.tiny_url;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scdt.url.common.ddd.Command;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class CreateTinyUrlCommand implements Command {
    @Valid
    @NotBlank(message = "原始链接不能为空")
    @Pattern(regexp = "^(http(s)?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?", message = "非法的原始链接")
    private final String originalUrl;

    @JsonCreator
    public CreateTinyUrlCommand(@JsonProperty("originalUrl") String originalUrl) {
        this.originalUrl = originalUrl;
    }
}
