package com.wangxiao.shortlink.domain.shortlink;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LinkPair {

    private String shortLink;

    private String longLink;
}
