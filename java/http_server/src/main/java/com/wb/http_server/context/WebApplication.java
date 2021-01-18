package com.wb.http_server.context;


/**
 * @author bing.wang
 * @date 2021/1/15
 */

public class WebApplication {
    private static ServletContext servletContext;
    
    static {
        servletContext = new ServletContext();
    }

    public static ServletContext getServletContext() {
        return servletContext;
    }
}
