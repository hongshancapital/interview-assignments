package com.eagle.shorturl.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author eagle
 * @description
 */

public class AuthUtil {

    private static final String SK = "eagle4h";

    public static String generate(String param) {
        return HashUtil.hmacSHA256(SK + param, param);
    }

    public static boolean validate(String param, String sign) {
        String generateSign = generate(param);
        return StringUtils.equals(sign, generateSign);
    }

}
