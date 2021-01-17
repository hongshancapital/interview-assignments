package com.wyd.http.server;

import com.wyd.http.servlet.DefaultServlet;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

public final class HttpServerConfig {
    private String location;
    private String servlet;

    private HttpServerConfig() {
    }

    private String alias;
    private String serverType;
    private int backLog;
    private int port;
    //executor.coresize=10
    //executor.maxsize=10
    //executor.keeplive=10000
    //executor.keeplive.type=1
    //executor.queuetype=1
    private int coreSize;
    private int maxsize;
    private int keeplive;
    private int queuetype;
    private int requestBytesize;
    private int queueSize;

    public BlockingQueue getQueue() {
        if (this.queuetype == 0) {
            return new ArrayBlockingQueue(queueSize == 0 ? 1024 : queueSize);
        }
        if (this.queuetype == 1) {
            return new LinkedBlockingQueue();
        }
        return new SynchronousQueue();
    }


    public int getRequestBytesize() {
        return requestBytesize;
    }

    public void setRequestBytesize(int requestBytesize) {
        this.requestBytesize = requestBytesize;
    }

    public int getCoreSize() {
        return coreSize;
    }

    public void setCoreSize(int coreSize) {
        this.coreSize = coreSize;
    }

    public int getMaxsize() {
        return maxsize;
    }

    public void setMaxsize(int maxsize) {
        this.maxsize = maxsize;
    }

    public int getKeeplive() {
        return keeplive;
    }

    public void setKeeplive(int keeplive) {
        this.keeplive = keeplive;
    }

    public int getQueuetype() {
        return queuetype;
    }

    public void setQueuetype(int queuetype) {
        this.queuetype = queuetype;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public void setServerType(String serverType) {

        this.serverType = serverType;
    }

    public String getServerType() {
        return serverType;
    }

    public void setBackLog(int backLog) {
        if (backLog == -1) {
            backLog = 1024;
        }
        this.backLog = backLog;
    }

    public int getBackLog() {
        return backLog;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        if (location == null) {
            return "/";
        }
        return location;
    }

    public void setServlet(String servlet) {

        this.servlet = servlet;
    }

    public DefaultServlet getServlet() {
        try {
            return (DefaultServlet) Class.forName(servlet).newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }


    public enum Sinalton {

        SINALTON;
        HttpServerConfig conf;

        Sinalton() {
            conf = new HttpServerConfig();
        }

        public HttpServerConfig getInstance() {
            return conf;
        }
    }

    public int getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }
}
