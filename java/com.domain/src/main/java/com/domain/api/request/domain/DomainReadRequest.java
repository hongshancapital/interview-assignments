package com.domain.api.request.domain;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class DomainReadRequest {

    @ApiModelProperty(value = "短域名", required = true, example = "", notes = "", dataType = "String")
    @NotEmpty
    private String shortUrl;


}
