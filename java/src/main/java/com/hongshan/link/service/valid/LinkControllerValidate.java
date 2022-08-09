package com.hongshan.link.service.valid;

import com.hongshan.link.service.constant.LinkConstant;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author heshineng
 * created by 2022/8/8
 * Controller 层校验
 */
public class LinkControllerValidate {

    /**
     * 检查url 是否合法
     *
     * @param url
     * @return
     */
    public static boolean checkUrl(String url) {
        if (url == null || url.isEmpty()) {
            return false;
        }
        try {
            new URL(url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    /**
     * 检查是否是短链接的url
     *
     * @param url
     * @return
     */
    public static boolean checkShortUrl(String url) {
        if (checkUrl(url)) {
            return url.startsWith(LinkConstant.SHORT_LINK_HOST);
        }
        return false;
    }
}
