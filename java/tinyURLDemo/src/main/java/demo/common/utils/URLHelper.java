package demo.common.utils;

import org.springframework.util.StringUtils;

public class URLHelper {
    public static final String SAFE_PREFIX = "https://";
    public static final String SAFE_TINY_PREFIX = "https://t.cn/";

    public static boolean validURL(String url) {
        if (StringUtils.isEmpty(url)) return false;
        return url.startsWith(SAFE_PREFIX);
    }

    public static boolean validTinyURLLength(String url, int maxLength, boolean movePrefix) {
        if (!url.startsWith(SAFE_TINY_PREFIX)) return false;

        return url.length() <= (maxLength + (movePrefix ? SAFE_TINY_PREFIX.length() : 0));
    }
}
