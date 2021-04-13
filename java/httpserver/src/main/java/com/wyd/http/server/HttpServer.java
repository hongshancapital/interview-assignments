package com.wyd.http.server;

import java.net.InetSocketAddress;

public interface HttpServer {

    void start(InetSocketAddress inetSocketAddress);

    void reload();
}
