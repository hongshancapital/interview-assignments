package com.wb.http_server.context.holder;

import com.wb.http_server.servlet.Servlet;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author bing.wang
 * @date 2021/1/15
 */

@Getter
@Setter
public class ServletHolder {
    private Servlet servlet;
    private String servletClass;

    public ServletHolder(String servletClass) {
        this.servletClass = servletClass;
    }
}
