package com.wb.http_server.network.handler;

import com.wb.http_server.context.ServletContext;
import com.wb.http_server.exception.ServerErrorException;
import com.wb.http_server.exception.ServletNotFoundException;
import com.wb.http_server.exception.base.ServletException;
import com.wb.http_server.exception.handler.ExceptionHandler;
import com.wb.http_server.network.wrapper.SocketWrapper;
import com.wb.http_server.request.Request;
import com.wb.http_server.resource.ResourceHandler;
import com.wb.http_server.response.Response;
import com.wb.http_server.servlet.Servlet;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


/**
 * @author bing.wang
 * @date 2021/1/15
 * RequestHandler 的父类，通过父类来复用成员变量和部分方法
 * 不同IO模型的RequestHandler基本是在将Response写回客户端这部分有不同的实现，在这里被设置为了抽象方法
 */
@Slf4j
@Getter
public abstract class AbstractRequestHandler implements Runnable {

    protected Request request;
    protected Response response;
    protected SocketWrapper socketWrapper;
    protected ServletContext servletContext;
    protected ExceptionHandler exceptionHandler;
    protected ResourceHandler resourceHandler;
    protected boolean isFinished;
    protected Servlet servlet;

    public AbstractRequestHandler(SocketWrapper socketWrapper, ServletContext servletContext, ExceptionHandler exceptionHandler, ResourceHandler resourceHandler, Request request, Response response)
            throws ServletNotFoundException {
        this.socketWrapper = socketWrapper;
        this.servletContext = servletContext;
        this.exceptionHandler = exceptionHandler;
        this.resourceHandler = resourceHandler;
        this.isFinished = false;
        this.request = request;
        this.response = response;
        request.setServletContext(servletContext);
        request.setRequestHandler(this);
        response.setRequestHandler(this);
        servlet = servletContext.mapServlet(request.getUrl());
    }

    /**
     * 入口
     */
    @Override
    public void run() {
        service();
    }
    /**
     * 调用servlet
     */
    private void service() {
        try {
            //处理资源，交由某个Servlet执行
            //Servlet是单例多线程
            //Servlet在RequestHandler中执行
            servlet.service(request, response);
        } catch (ServletException e) {
            exceptionHandler.handle(e, response, socketWrapper);
        } catch (Exception e) {
            //其他未知异常
            e.printStackTrace();
            exceptionHandler.handle(new ServerErrorException(), response, socketWrapper);
        } finally {
            if (!isFinished) {
                flushResponse();
            }
        }
        log.info("请求处理完毕");
    }

    /**
     * 响应数据写回到客户端并关闭连接
     */
    public abstract void flushResponse();

    /**
     * 响应数据写回到客户端
     */
    public abstract void flushBuffer();
}
