package com.wyd.http.server;


import com.wyd.http.server.metric.WydHttpMetric;
import com.wyd.http.server.net.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class LearnNginxHttpServerImpl implements HttpServer {

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private volatile boolean open = false;
    private HttpServerConfig config;

    public LearnNginxHttpServerImpl() {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public void start(InetSocketAddress inetSocketAddress) {
        try {
            serverSocketChannel.bind(inetSocketAddress, this.config.getBackLog());
            serverSocketChannel.configureBlocking(false);
            WydHttpMetric.getInstance().queueMonitor(RequestQueue.class, RequestQueue.getRequestQueue().getQueue(), "acceptor");
            WydHttpMetric.getInstance().queueMonitor(ResponseQueue.class, ResponseQueue.getResponseQueue().getQueue(), "poller");
            Acceptor acceptor = new Acceptor(serverSocketChannel);
            acceptor.setDaemon(true);
            acceptor.setName("Acceptor");

            Poller poller = new Poller();
            poller.setDaemon(true);
            poller.setName("Poller");

            ProcessRequest processRequest = new ProcessRequest();
            processRequest.setDaemon(true);
            processRequest.setName("Process-Request");

            acceptor.start();
            poller.start();
            processRequest.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reload() {
        this.config = HttpServerConfig.Sinalton.SINALTON.getInstance();
    }
}
