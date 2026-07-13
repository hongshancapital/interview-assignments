package com.liupf.tiny.url.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;


/**
 * 短链接Domain
 */
@ToString
@Getter
@AllArgsConstructor
public class TinyURL {

    private String code;

    private String longUrl;

}
