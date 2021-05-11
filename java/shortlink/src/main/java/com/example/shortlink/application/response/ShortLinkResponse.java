package com.example.shortlink.application.response;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@ApiModel(value = "ShortLink返回实体", description = "短链描述类")
public class ShortLinkResponse {

    private String longLink;
    private String shortLink;
}
