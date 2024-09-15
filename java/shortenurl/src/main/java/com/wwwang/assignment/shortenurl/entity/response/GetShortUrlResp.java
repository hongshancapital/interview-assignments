package com.wwwang.assignment.shortenurl.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.io.Serializable;

@ApiModel(description = "短域名实体")
@Getter
public class GetShortUrlResp implements Serializable {

    @ApiModelProperty(value="短域名", example="AcI15A")
    private String shortUrl;

    public GetShortUrlResp(String shortUrl){
        this.shortUrl = shortUrl;
    }

}
