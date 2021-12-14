package com.scdt.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;

public class DigestUtil {
    public static final String DEFAULT_ENCODING = "UTF-8";

    private DigestUtil() {
    }

    public static String md5(String content) {
        if (StringUtils.isEmpty(content)) {
            return "";
        }
        try {
            return DigestUtils.md5DigestAsHex(content.getBytes(DEFAULT_ENCODING));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
