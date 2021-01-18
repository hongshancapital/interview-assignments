

package com.wb.http_server.listener;

import com.wb.http_server.listener.event.HttpSessionEvent;

import java.util.EventListener;

/**
 * @author bing.wang
 * @Date 2021/1/15
 */
public interface HttpSessionListener extends EventListener {
    /**
     * session创建
     * @param se
     */
    void sessionCreated(HttpSessionEvent se);

    /**
     * session销毁
     * @param se
     */
    void sessionDestroyed(HttpSessionEvent se);
    
}

