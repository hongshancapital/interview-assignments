package com.mjl.model;

import lombok.Data;

import java.io.Serializable;


@Data
public class GetShortUrlRequest implements Serializable {

    private String longUrl;

}
