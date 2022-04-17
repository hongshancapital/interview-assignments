package com.david.urlconverter.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * URL格式校验
 */
public class UrlUtils {

    static final String LONG_URL_REGEX = "^((https|http|ftp|rtsp|mms)?://)"
            + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?"
            + "(([0-9]{1,3}\\.){3}[0-9]{1,3}"
            + "|" + "([0-9a-z_!~*'()-]+\\.)*"
            + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\."
            + "[a-z]{2,6})" + "(:[0-9]{1,5})?"
            + "((/?)|" + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";

    static final String SHORT_URL_REGEX = "[a-zA-Z0-9]{1,8}=*$";

    static final String PLACE_HOLDER = "=";

    static final int MAX_SHORT_LENGTH = 8;
    /**
     * 校验长域名合法性
     * @param longUrl
     * @return
     */
    public static boolean isValidLongUrl( String longUrl){

        if (StringUtils.isBlank(longUrl)) {
            return false;
        }

        return longUrl.matches(LONG_URL_REGEX);
    }

    /**
     * 移除短域名填位符=，然后校验短域名合法性
     * @param shortUrl
     * @return
     */
    public static boolean isValidShortUrl(String shortUrl){

        if (StringUtils.isBlank(shortUrl)) {
            return false;
        }

        if (shortUrl.trim().length() != 8) {
            return false;
        }


        return shortUrl.matches(SHORT_URL_REGEX);
    }

    /**
     *
     * @param shortUrl
     * @return
     */
    public static String setPlaceHolder(String shortUrl){
        return StringUtils.rightPad(shortUrl,MAX_SHORT_LENGTH, PLACE_HOLDER);
    }
}
