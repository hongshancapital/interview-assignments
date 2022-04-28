package com.web.work.common.enums;

import java.io.Serializable;

/**
 * http header enum
 *
 * @author chenze
 * @version 1.0
 * @date 2022/4/27 8:42 PM
 */
public enum HttpHeaderEnum implements Serializable {
    // start with http
    HTTP("http"),
    // start with https
    HTTPS("https");

    private final String name;

    HttpHeaderEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
