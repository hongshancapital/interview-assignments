package com.domain.api.request.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class DomainWriteRequest {

    @ApiModelProperty(value = "长域名", required = true, example = "", notes = "", dataType = "String")
    @NotEmpty
    private String longUrl;
}
