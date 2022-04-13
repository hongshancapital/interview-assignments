package com.getao.urlconverter.dto.vo;

import lombok.Data;

@Data
public class GetShortUrlVO {

    public GetShortUrlVO(int status, String shortUrl, String description){
        this.status = status;
        this.shortUrl = shortUrl;
        this.description = description;
    }

    private int status;

    private String shortUrl;

    private String description;
}