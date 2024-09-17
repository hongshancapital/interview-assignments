package com.liuwangpan.urlconvert.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * @Deacription url工具类
 * @author wp_li
 **/
public class UrlUtil {

    static Pattern pattern = Pattern
            .compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\\\\\\\/])+$");

    /**
     * 校验是否是url地址
     *
     * @param url
     * @return
     */
    public static boolean isValidationUrl(String url) {
        if (StringUtils.isBlank(url)) {
            return false;
        }
        return pattern.matcher(url).matches();
    }

}