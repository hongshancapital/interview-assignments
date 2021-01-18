package com.wb.http_server.network.wrapper;

import com.wb.http_server.network.HTTPServer;
import com.wb.http_server.network.connector.NioPoller;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * @author bing.wang
 * @date 2021/1/15
 */
@Slf4j
@Getter
@Setter
public class NioSocketWrapper implements SocketWrapper {
    private final HTTPServer server;
    private final SocketChannel socketChannel;
    private final NioPoller nioPoller;
    private final boolean isNewSocket;
    private volatile long waitBegin;
    private volatile boolean isWorking;
    
    public NioSocketWrapper(HTTPServer server, SocketChannel socketChannel, NioPoller nioPoller, boolean isNewSocket) {
        this.server = server;
        this.socketChannel = socketChannel;
        this.nioPoller = nioPoller;
        this.isNewSocket = isNewSocket;
        this.isWorking = false;
    }
    
    public void close() throws IOException,NullPointerException {
        SelectionKey key = socketChannel.keyFor(nioPoller.getSelector());
        if(null!=key){
            key.cancel();
        }
        socketChannel.close();
    }



    @Override
    public String toString() {
        return socketChannel.toString();
    }
}
