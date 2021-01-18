package com.wb.http_server.dispatcher;

import com.wb.http_server.context.ServletContext;
import com.wb.http_server.context.WebApplication;
import com.wb.http_server.exception.handler.ExceptionHandler;
import com.wb.http_server.network.wrapper.SocketWrapper;
import com.wb.http_server.resource.ResourceHandler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author bing.wang
 * @date 2021/1/15
 * 所有Dispatcher（请求分发器）的父类
 */


public abstract class AbstractDispatcher {
    protected ResourceHandler resourceHandler;
    protected ExceptionHandler exceptionHandler;
    protected ThreadPoolExecutor pool;
    protected ServletContext servletContext;
    
    public AbstractDispatcher() {
        this.servletContext = WebApplication.getServletContext();
        this.exceptionHandler = new ExceptionHandler();
        this.resourceHandler = new ResourceHandler(exceptionHandler);
        ThreadFactory threadFactory = new ThreadFactory() {
            private int count;

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "Worker Pool-" + count++);
            }
        };
        this.pool = new ThreadPoolExecutor(100, 100, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(200), threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
    }
    
    /**
     * 关闭
     */
    public void shutdown() {
        pool.shutdown();
        servletContext.destroy();
    }

    /**
     * 分发请求
     * @param socketWrapper
     */
    public abstract void doDispatch(SocketWrapper socketWrapper);
}
