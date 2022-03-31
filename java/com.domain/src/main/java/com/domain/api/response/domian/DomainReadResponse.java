package com.domain.api.response.domian;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DomainReadResponse {

    @ApiModelProperty(value = "长域名", required = true, example = "", notes = "", dataType = "String")
    private String longUrl;
}
