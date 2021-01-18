package com.wb.http_server.network.connector;

import com.wb.http_server.network.HTTPServer;
import com.wb.http_server.network.wrapper.NioSocketWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author bing.wang
 * @date 2021/1/15
 */

@Slf4j
public class NioPoller implements Runnable {
    private HTTPServer httpserver;
    private Selector selector;
    private Queue<PollerEvent> events;
    private String pollerName;
    private Map<SocketChannel, NioSocketWrapper> sockets;
    
    public NioPoller(HTTPServer httpserver, String pollerName) throws IOException {
        this.sockets = new ConcurrentHashMap<>();
        this.httpserver = httpserver;
        this.selector = Selector.open();
        this.events = new ConcurrentLinkedQueue<>();
        this.pollerName = pollerName;
    }

    /**
     * 注册一个新的或旧的socket至Poller中
     * 注意，只有在这里会初始化或重置waitBegin
     * @param socketChannel
     * @param isNewSocket
     */
    public void register(SocketChannel socketChannel, boolean isNewSocket) {
        log.info("Acceptor将连接到的socket放入 {} 的Queue中", pollerName);
        NioSocketWrapper wrapper;
        if (isNewSocket) {
            // 设置waitBegin
            wrapper = new NioSocketWrapper(httpserver, socketChannel, this, isNewSocket);
            // 用于cleaner检测超时的socket和关闭socket
            sockets.put(socketChannel, wrapper);
        } else {
            wrapper = sockets.get(socketChannel);
            wrapper.setWorking(false);
        }
        wrapper.setWaitBegin(System.currentTimeMillis());
        events.offer(new PollerEvent(wrapper));
        // 某个线程调用select()方法后阻塞了，即使没有通道已经就绪，也有办法让其从select()方法返回。只要让其它线程在第一个线程调用select()方法的那个对象上调用Selector.wakeup()方法即可。阻塞在select()方法上的线程会立马返回。
        selector.wakeup();
    }

    public void close() throws IOException {
        for (NioSocketWrapper wrapper : sockets.values()) {
            wrapper.close();
        }
        events.clear();
        selector.close();
    }
    
    @Override
    public void run() {
        log.info("{} 开始监听", Thread.currentThread().getName());
        while (httpserver.isRunning()) {
            try {
                events();
                if (selector.select() <= 0) {
                    continue;
                }
                log.info("select()返回,开始获取当前选择器中所有注册的监听事件");
                //获取当前选择器中所有注册的监听事件
                for (Iterator<SelectionKey> it = selector.selectedKeys().iterator(); it.hasNext(); ) {
                    SelectionKey key = it.next();
                    //如果"接收"事件已就绪
                    if(!key.isValid()){
                        continue;
                    }
                    if (key.isReadable()) {
                        //如果"读取"事件已就绪
                        //交由读取事件的处理器处理
                        log.info("serverSocket读已就绪,准备读");
                        NioSocketWrapper attachment = (NioSocketWrapper) key.attachment();
                        if (attachment != null) {
                            processSocket(attachment);
                        }
                    }
                    //处理完毕后，需要取消当前的选择键
                    it.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClosedSelectorException e) {
                log.info("{} 对应的selector 已关闭", this.pollerName);
            }
        }
    }

    private void processSocket(NioSocketWrapper attachment) {
        attachment.setWorking(true);
        httpserver.execute(attachment);
    }

    private boolean events() {
        log.info("Queue大小为{},清空Queue,将连接到的Socket注册到selector中", events.size());
        boolean result = false;
        PollerEvent pollerEvent;
        for (int i = 0, size = events.size(); i < size && (pollerEvent = events.poll()) != null; i++) {
            result = true;
            pollerEvent.run();
        }
        return result;
    }

    public Selector getSelector() {
        return selector;
    }

    public String getPollerName() {
        return pollerName;
    }
    
    public void cleanTimeoutSockets() {
        for (Iterator<Map.Entry<SocketChannel, NioSocketWrapper>> it = sockets.entrySet().iterator(); it.hasNext(); ) {
            NioSocketWrapper wrapper = it.next().getValue();
            log.debug("缓存中的socket:{}", wrapper);
            if (!wrapper.getSocketChannel().isConnected()) {
                log.debug("该socket已被关闭");
                it.remove();
                continue;
            }
            if (wrapper.isWorking()) {
                log.debug("该socket正在工作中，不予关闭");
                continue;
            }
            if (System.currentTimeMillis() - wrapper.getWaitBegin() > httpserver.getKeepAliveTimeout()) {
                // 反注册
                log.info("{} keepAlive已过期", wrapper.getSocketChannel());
                try {
                    wrapper.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                it.remove();
            }
        }
    }
    


    @AllArgsConstructor
    private static class PollerEvent implements Runnable {
        private NioSocketWrapper wrapper;

        @Override
        public void run() {
            log.info("将SocketChannel的读事件注册到Poller的selector中");
            try {
                if (wrapper.getSocketChannel().isOpen()) {
                    wrapper.getSocketChannel().register(wrapper.getNioPoller().getSelector(), SelectionKey.OP_READ, wrapper);
                } else {
                    log.error("socket已经被关闭，无法注册到Poller", wrapper.getSocketChannel());
                }
            } catch (ClosedChannelException e) {
                e.printStackTrace();
            }
        }
    }
}
