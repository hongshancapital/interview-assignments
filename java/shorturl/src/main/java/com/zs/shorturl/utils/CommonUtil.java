package com.zs.shorturl.utils;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * 公共的帮助方法
 *
 * @author zs
 * @date 2021/5/10
 */
public class CommonUtil {

    /**
     * 验证url 是否合法
     *
     * @param url
     * @return
     */
    public static boolean isValidUrl(String url) {
        URL u = null;
        try {
            u = new URL(url);
        } catch (MalformedURLException e) {
            return false;
        }
        try {
            u.toURI();
        } catch (URISyntaxException e) {
            return false;
        }
        return true;
    }


}
