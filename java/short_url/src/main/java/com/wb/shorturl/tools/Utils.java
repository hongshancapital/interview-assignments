package com.wb.shorturl.tools;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author bing.wang
 * @date 2021/1/8
 */
public class Utils {

    private static String urlRegex = "(http(s?)\\:\\/\\/[0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*(:(0-9)*)*(\\/?)([a-zA-Z0-9\\-\\.\\?\\,\\'\\/\\\\&%\\+\\$#_=]*))?";

    /**
     * 判断是否是URL
     * @param url
     * @return the match result
     */
    public static boolean isUrl(String url) {
        if(StringUtils.isBlank(url)) {
            return  false;
        }
        Pattern pat = Pattern.compile(urlRegex);
        Matcher mat = pat.matcher(url.trim());
        return mat.matches();
    }
}
