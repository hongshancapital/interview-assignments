package com.scdt.shortlink.core.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Url的工具方法
 *
 * @Author tzf
 * @Date 2022/4/28
 */
public class LinkUtils {
    private static final String LINK_SPLIT_SYMBOL = "?";
    private static final String HTTP_PROTOCOL = "http://";
    private static final String HTTPS_PROTOCOL = "https://";

    /**
     * 删除短链的参数：比如页面埋点等参数
     *
     * @param link
     * @return
     */
    public static String removeParam(String link) {
        if (StringUtils.isEmpty(link)) {
            return StringUtils.EMPTY;
        }

        if (!StringUtils.contains(link, LINK_SPLIT_SYMBOL)) {
            return link;
        }

        return StringUtils.substringBefore(link, LINK_SPLIT_SYMBOL);
    }

    /**∂
     * 检查链接规则
     * 判断链接头部由 "http://" 或 "https://" 开始
     *
     * @param link
     * @return
     */
    public static boolean checkLinkRule(String link) {
        if (StringUtils.startsWithIgnoreCase(link, HTTP_PROTOCOL) || StringUtils.startsWithIgnoreCase(link, HTTPS_PROTOCOL)) {
            return true;
        }

        return false;
    }

}
