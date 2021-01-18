
package com.wb.http_server.listener.event;


import com.wb.http_server.session.HttpSession;

/**
 * @author bing.wang
 * @date 2021/1/15
 * session相关的事件
 */
public class HttpSessionEvent extends java.util.EventObject {

    private static final long serialVersionUID = -7622791603672342895L;

   
    public HttpSessionEvent(HttpSession source) {
        super(source);
    }
    
    public HttpSession getSession () {
        return (HttpSession) super.getSource();
    }
}

