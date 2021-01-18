package com.wb.http_server.servlet.impl;

import com.wb.http_server.enumeration.HttpStatus;
import com.wb.http_server.enumeration.RequestMethod;
import com.wb.http_server.exception.base.ServletException;
import com.wb.http_server.request.Request;
import com.wb.http_server.response.Header;
import com.wb.http_server.response.Response;
import com.wb.http_server.servlet.Servlet;
import com.wb.http_server.util.Base64;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author bing.wang
 * @date 2021/1/15
 * 如果当前url没有匹配任何的servlet，就会调用默认Servlet，它可以处理静态资源
 */
@Slf4j
public class DefaultServlet implements Servlet {

    @Override
    public void init() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void service(Request request, Response response) throws ServletException, IOException {
        Object sessionAuth =request.getSession().getAttribute("auth");
        if (sessionAuth == null) {
            if(!checkHeaderAuth(request)){
                response.setStatus(HttpStatus.NOT_AUTH);
                response.addHeader(new Header("Cache-Control", "no-store"));
                response.addHeader(new Header("WWW-authenticate", "Basic Realm=\"input username and password\""));
                return;
            }
        }

        if (request.getMethod() == RequestMethod.GET){
            //首页
            if (request.getUrl().equals("/http_server")||request.getUrl().equals("/http_server/")) {
                request.setUrl("/http_server/file/index.html");
            }
            request.getRequestDispatcher(request.getUrl()).forward(request, response);
        }
    }
    private boolean checkHeaderAuth(Request request) {
        String auth = request.getHeader("Authorization");
        if ((auth != null) && (auth.length() > 6)) {
            auth = auth.substring(6, auth.length());
            String decodedAuth = Base64.getFromBASE64(auth);
            if(decodedAuth.equals("test:test")){
                request.getSession().setAttribute("auth", decodedAuth);
                return true;
            }
        }
        return false;
    }
}
