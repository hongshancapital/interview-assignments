package com.mjl.model;

import lombok.Data;

import java.io.Serializable;


@Data
public class GetLongUrlRequest implements Serializable {
    private String shortUrlSuffix;
    private String shortUrl;
}
