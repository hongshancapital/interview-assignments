package com.wb.http_server.network.connector;

import com.wb.http_server.network.HTTPServer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * @author bing.wang
 * @date 2021/1/15
 * Nio 请求器
 */
@Slf4j
public class NioAcceptor implements Runnable {
    private HTTPServer httpserver;
    
    public NioAcceptor(HTTPServer httpserver) {
        this.httpserver = httpserver;
    }
    
    @Override
    public void run() {
        log.info("{} 开始监听",Thread.currentThread().getName());
        while (httpserver.isRunning()) {
            SocketChannel client;
            try {
                client = httpserver.accept();
                if(client == null){
                    log.info("Acceptor接收到连接请求 {}",client);
                    continue;
                }
                client.configureBlocking(false);
                log.info("Acceptor接收到连接请求 {}",client);
                httpserver.registerToPoller(client);
                log.info("socketWrapper:{}", client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
