package com.wyd.http.server.net;

import java.util.HashMap;
import java.util.Map;

public final class Request {

    private final Map<String, byte[]> header = new HashMap<>();
    private String method;
    private String path;
    private String acceptor;
    private String authenticate;
    private Session session=new Session();

    public Session getSession() {
        return session;
    }

    public Map<String, byte[]> getHeader() {
        return header;
    }

    public String getAuthenticate() {
        return authenticate;
    }

    public void setAuthenticate(String authenticate) {
        this.authenticate = authenticate;
    }

    public String getAcceptor() {
        return acceptor;
    }

    public void setAcceptor(String acceptor) {
        this.acceptor = acceptor;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
