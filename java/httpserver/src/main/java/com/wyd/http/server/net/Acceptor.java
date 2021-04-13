package com.wyd.http.server.net;

import com.wyd.http.server.HttpServerConfig;
import com.wyd.http.server.metric.WydHttpMetric;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class Acceptor extends Thread {

    private ServerSocketChannel serverSocketChannel;
    private volatile boolean flag = false;
    private Selector selector;

    public Acceptor(ServerSocketChannel serverSocketChannel) {
        this.serverSocketChannel = serverSocketChannel;

        try {
            this.selector = Selector.open();
            this.serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopAcceptor() {
        this.flag = true;
    }

    @Override
    public void run() {
        HttpServerConfig instance = HttpServerConfig.Sinalton.SINALTON.getInstance();
        while (!flag) {
            try {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    if (selectionKey.isValid()) {
                        if (selectionKey.isAcceptable()) {
                            ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                            SocketChannel accept = channel.accept();
                            ByteBuffer allocate = ByteBuffer.allocate(instance.getRequestBytesize());
                            accept.read(allocate);
                            accept.configureBlocking(false);
                            SelectionKey register = accept.register(selector, SelectionKey.OP_READ);
                            RequestEvent requestEvent = new RequestEvent();
                            requestEvent.setSelector(selector);
                            requestEvent.setSelectionKey(register);
                            requestEvent.setSocketChannel(accept);
                            requestEvent.setOps(SelectionKey.OP_READ);
                            requestEvent.setByteBuffer(allocate);
                            RequestQueue.getRequestQueue().add(requestEvent);
                        }
                    } else {
                        selectionKey.cancel();
                    }
                }
            } catch (IOException | CancelledKeyException e) {
                e.printStackTrace();
            }

        }
    }
}
