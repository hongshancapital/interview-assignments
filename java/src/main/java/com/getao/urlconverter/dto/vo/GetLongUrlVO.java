package com.getao.urlconverter.dto.vo;

import lombok.Data;

@Data
public class GetLongUrlVO {

    public GetLongUrlVO(int status, String longUrl, String description) {
        this.status = status;
        this.longUrl = longUrl;
        this.description = description;
    }

    private int status;

    private String longUrl;

    private String description;
}