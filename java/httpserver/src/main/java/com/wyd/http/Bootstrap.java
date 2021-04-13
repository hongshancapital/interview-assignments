package com.wyd.http;

import com.wyd.http.server.HttpServer;
import com.wyd.http.server.HttpServerConfig;
import com.wyd.http.server.factory.ServerFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class Bootstrap {
    private static Bootstrap bootstrap = null;

    private static final Properties properties = new Properties();

    private static HttpServer httpServer;
    private static final CountDownLatch latch = new CountDownLatch(1);

    public HttpServerConfig init(String path) {
        HttpServerConfig config = HttpServerConfig.Sinalton.SINALTON.getInstance();
        try (FileInputStream fileInputStream = new FileInputStream(path)) {
            properties.load(fileInputStream);
            String alias = properties.getProperty("alias");
            String serverType = properties.getProperty("server");
            String backlog = properties.getProperty("backlog");
            String port = properties.getProperty("port");
            config.setAlias(alias);
            config.setServerType(serverType);
            config.setPort(port == null ? 3555 : Integer.valueOf(port));
            config.setBackLog(backlog == null ? -1 : Integer.valueOf(backlog));
            //executor.coresize=10
            //executor.maxsize=10
            //#default millsecond
            //executor.keeplive=1000
            //executor.queuetype=1
            //executor.queue.size=length
            String coresize = properties.getProperty("executor.coresize");
            String keeplive = properties.getProperty("executor.keeplive");
            String maxsize = properties.getProperty("executor.maxsize");
            String queuetype = properties.getProperty("executor.queuetype");
            String queueSize = properties.getProperty("executor.queue.size");
            config.setCoreSize(coresize == null ? Runtime.getRuntime().availableProcessors() : Integer.valueOf(coresize));
            config.setMaxsize(coresize == null ? Runtime.getRuntime().availableProcessors() : Integer.valueOf(maxsize));
            config.setKeeplive(keeplive == null ? 1000 : Integer.valueOf(keeplive));
            config.setQueuetype(queuetype == null ? 0 : Integer.valueOf(queuetype));
            config.setQueueSize(queueSize == null ? 100 : Integer.valueOf(queueSize));
            //request.bytesize
            String requestBytesize = properties.getProperty("request.bytesize");

            config.setRequestBytesize(requestBytesize == null ? 1024 : Integer.valueOf(requestBytesize));
            //match.location
            String location = properties.getProperty("match.location");
            config.setLocation(location);
            //servlet
            String servlet = properties.getProperty("servlet");
            config.setServlet(servlet);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return config;


    }

    public void start(InetSocketAddress inetSocketAddress) {
        HttpServerConfig instance = HttpServerConfig.Sinalton.SINALTON.getInstance();
        httpServer = ServerFactory.getServer(instance.getServerType());
        httpServer.reload();
        httpServer.start(inetSocketAddress);
    }

    public void stop() {
        latch.countDown();
    }

    public static void main(String[] args) {
        if (bootstrap == null) {
            bootstrap = new Bootstrap();
            bootstrap.init(args[1]);
        }
        HttpServerConfig instance = HttpServerConfig.Sinalton.SINALTON.getInstance();
        String command = args[0];
        if (command.equals("start")) {
            bootstrap.start(new InetSocketAddress(instance.getPort()));
        }
        if (command.equals("stop")) {
            bootstrap.stop();
        }

        if (command.equals("reload")) {
            bootstrap.init(args[1]);
            httpServer.reload();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
