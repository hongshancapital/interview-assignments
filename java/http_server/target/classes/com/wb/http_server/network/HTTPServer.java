package com.wb.http_server.network;

import com.wb.http_server.dispatcher.NioDispatcher;
import com.wb.http_server.network.connector.IdleConnectionCleaner;
import com.wb.http_server.network.connector.NioAcceptor;
import com.wb.http_server.network.connector.NioPoller;
import com.wb.http_server.network.wrapper.NioSocketWrapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author bing.wang
 * @date 2021/1/15
 * 请求层面上的监听器
 */

@Slf4j
public class HTTPServer {
    private int pollerCount = Math.min(2, Runtime.getRuntime().availableProcessors());
    private ServerSocketChannel server;
    private NioDispatcher nioDispatcher;
    private volatile boolean isRunning = true;
    private NioAcceptor nioAcceptor;
    private List<NioPoller> nioPollers;

    /**
     * poller轮询器
     */
    private AtomicInteger pollerRotater = new AtomicInteger(0);

    /**
     * 1min
     */
    private int keepAliveTimeout = 60 * 1000 ;

    private IdleConnectionCleaner cleaner;

    //********************************初始化*************************************************************
    private void initDispatcherServlet() {
        nioDispatcher = new NioDispatcher();
    }

    /**
     * 初始化IdleSocketCleaner
     */
    private void initIdleSocketCleaner() {
        cleaner = new IdleConnectionCleaner(nioPollers);
        cleaner.start();
    }

    private void initServerSocket(int port) throws IOException {
        server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(port));
        server.configureBlocking(true);
    }

    private void initPoller() throws IOException {
        nioPollers = new ArrayList<>(pollerCount);
        for (int i = 0; i < pollerCount; i++) {
            String pollName = "NioPoller-" + i;
            NioPoller nioPoller = new NioPoller(this, pollName);
            Thread pollerThread = new Thread(nioPoller, pollName);
            pollerThread.setDaemon(true);
            pollerThread.start();
            nioPollers.add(nioPoller);
        }
    }

    /**
     * 初始化Acceptor
     */
    private void initAcceptor() {
        this.nioAcceptor = new NioAcceptor(this);
        Thread t = new Thread(nioAcceptor, "NioAcceptor");
        t.setDaemon(true);
        t.start();
    }


    //************************初始化结束***************************************************************
    public void start(int port) {
        try {
            initDispatcherServlet();
            initServerSocket(port);
            initPoller();
            initAcceptor();
            initIdleSocketCleaner();
            log.info("服务器启动");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("初始化服务器失败");
            close();
        }
    }

    public void close() {
        isRunning = false;
        for (NioPoller nioPoller : nioPollers) {
            try {
                nioPoller.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        nioDispatcher.shutdown();
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 调用dispatcher，处理这个读已就绪的客户端连接
     *
     * @param socketWrapper
     */
    public void execute(NioSocketWrapper socketWrapper) {
        nioDispatcher.doDispatch(socketWrapper);
    }

    /**
     * 轮询Poller，实现负载均衡
     *
     * @return
     */
    private NioPoller getPoller() {
        int idx = Math.abs(pollerRotater.incrementAndGet()) % nioPollers.size();
        return nioPollers.get(idx);
    }

    public boolean isRunning() {
        return isRunning;
    }

    /**
     * 以阻塞方式来接收一个客户端的链接
     *
     * @return
     * @throws IOException
     */
    public SocketChannel accept() throws IOException {
        return server.accept();
    }

    /**
     * 将Acceptor接收到的socket放到轮询到的一个Poller的Queue中
     *
     * @param socket
     * @return
     */
    public void registerToPoller(SocketChannel socket) throws IOException {
        server.configureBlocking(false);
        getPoller().register(socket, true);
        server.configureBlocking(true);
    }
    public int getKeepAliveTimeout() {
        return this.keepAliveTimeout;
    }
}
