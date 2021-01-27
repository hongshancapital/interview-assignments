package com.luman.http.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) {
        new HttpServer().start();

    }

    private int port;

    public HttpServer() {
        this(DEFAULT_PORT);
    }

    public HttpServer(int port) {
        this.port = port;
    }

    private ServerSocket server;
    public void start() {
        try {
            server = new ServerSocket(port);
            ExecutorService ex = Executors.newFixedThreadPool(1000);
            for (; ; ) {
                ex.submit(new HttpHandler(server.accept()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void stop(){
        if(server!=null){
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
