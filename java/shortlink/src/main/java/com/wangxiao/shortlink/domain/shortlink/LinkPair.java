package com.wangxiao.shortlink.domain.shortlink;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LinkPair {

    private String shortLink;

    private String longLink;
}
