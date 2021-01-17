package com.wyd.http.server.factory;

import com.wyd.http.server.HttpServer;
import com.wyd.http.server.LearnNginxHttpServerImpl;

public final class ServerFactory {


    public static HttpServer getServer(String type) {
        LearnNginxHttpServerImpl nginxHttpServer = new LearnNginxHttpServerImpl();
        if (type == null || type.equals("")) {
            return nginxHttpServer;
        }
        if (type.equals("learnNginx")) {
            return nginxHttpServer;
        }
        return null;
    }

//    public static Poller getPoller(String type) {
//        Poller poller = new MyPoller();
//        if (type == null || type.equals("")) {
//            return poller;
//        }
//        if (type.equals("myPoller")) {
//            return poller;
//        }
//        return null;
//    }
//
//    public static Acceptor getAcceptor(String type) {
//        Acceptor acceptor = new MyAcceptor();
//        if (type == null || type.equals("")) {
//            return acceptor;
//        }
//        if (type.equals("myAcceptor")) {
//            return acceptor;
//        }
//        return null;
//    }
}
