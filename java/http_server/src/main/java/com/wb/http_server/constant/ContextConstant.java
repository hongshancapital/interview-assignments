package com.wb.http_server.constant;

/**
 * @author bing.wang
 * @date 2021/1/15
 */
public class ContextConstant {
    public static final String ERROR_PAGE = "/http_server/errors/%s.html";
    public static final String DEFAULT_CONTENT_TYPE = "text/html;charset=utf-8";
    public static final String DEFAULT_SERVLET_ALIAS = "DefaultServlet";
    /**
     * 300s 5min 过期
     */
    public static final int DEFAULT_SESSION_EXPIRE_TIME = 300;
}
