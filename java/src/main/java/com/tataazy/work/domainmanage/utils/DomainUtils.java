package com.tataazy.work.domainmanage.utils;

import org.apache.commons.lang3.StringUtils;
import java.util.regex.*;

public class DomainUtils {
    private static final Pattern URL_PATTERN = Pattern.compile("(http|https|ftp):\\/\\/(\\w+:{0,1}\\w*@)?(\\S+)(:[0-9]+)?(\\/|\\/([\\w#!:.?+=&%@!\\-\\/]))?");

    private static final int URL_MAX_LENGTH = 8192;

    public static boolean isUrlValid(String url) {
        if (StringUtils.isBlank(url)) {
            return false;
        }
        if (url.length() > URL_MAX_LENGTH) {
            return false;
        }
        return URL_PATTERN.matcher(url).matches();
    }

}
