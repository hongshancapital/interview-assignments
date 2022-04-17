package com.tb.link.infrastructure.util;

/**
 * @author andy.lhc
 * @date 2022/4/16 23:43
 */
public class HttpUtil {

    public static  boolean checkHttpAndHttps(String url){
        return isHttp(url) || isHttps(url) ;
    }
    public static boolean isHttps(String url) {
        return url.toLowerCase().startsWith("https:");
    }

    public static boolean isHttp(String url) {
        return url.toLowerCase().startsWith("http:");
    }

}
