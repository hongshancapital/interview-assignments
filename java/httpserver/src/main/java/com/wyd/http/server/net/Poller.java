package com.wyd.http.server.net;

import com.wyd.http.server.HttpServerConfig;
import com.wyd.http.server.metric.WydHttpMetric;
import org.apache.commons.logging.impl.Log4JLogger;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public class Poller extends Thread {

    private ThreadPoolExecutor threadPoolExecutor;
    private static final String POINT = ".";


    public Poller() {
        HttpServerConfig instance = HttpServerConfig.Sinalton.SINALTON.getInstance();
        threadPoolExecutor = new ThreadPoolExecutor(instance.getCoreSize(), instance.getMaxsize(),
                instance.getKeeplive(), TimeUnit.MILLISECONDS, instance.getQueue());

    }

    @Override
    public void run() {
        while (true) {
            Queue<RequestEvent> queue = RequestQueue.getRequestQueue().getQueue();
            for (int i = 0; i < queue.size(); i++) {
                RequestEvent poll = queue.poll();
                threadPoolExecutor.execute(() -> {

                    if(poll!=null){
                        Request request = new Request();
                        Response response = processRequest(request, poll.getByteBuffer());
                        sendResponse(response, poll.getSocketChannel(), poll.getSelector());
                        request = null;
                        response = null;
                    }

                });
            }
        }
    }


    public void sendResponse(Response response, SocketChannel channel, Selector selector) {
        try {
            channel.configureBlocking(false);
            SelectionKey register = channel.register(selector, SelectionKey.OP_WRITE, response);
            ResponseEvent responseEvent = new ResponseEvent();
            responseEvent.setSelectionKey(register);
            responseEvent.setSelector(selector);
            responseEvent.setOps(SelectionKey.OP_WRITE);
            responseEvent.setSocketChannel(channel);
            ResponseQueue.getResponseQueue().add(responseEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isLine(int index, int next) {
        return index == 13 && next == 10;
    }


    /**
     * 只解析需要的字段，提升性能
     *
     * @param allocate
     */
    public Response processRequest(Request request, ByteBuffer allocate) {
        HttpServerConfig instance = HttpServerConfig.Sinalton.SINALTON.getInstance();
        Response response = new Response();
        try {
            // 解析请求头
            int start = 0;
            int levelNum = 0;
            int center = 0;
            byte[] array = allocate.array();
            String key = null;
            byte[] values = null;
            String status = "200";
            for (int i = 0; i < allocate.capacity() - 1; i++) {
                if (levelNum <= 2) {
                    if (isLine(array[i], array[i + 1])) {
                        start = i + 2;
                        levelNum++;
                    } else if (array[i] == 32) {
                        if (levelNum == 1) {
                            byte[] bytes = new byte[i - start];
                            System.arraycopy(array, start, bytes, 0, bytes.length);
                            request.setPath(new String(bytes));
                        }
                        start = i + 1;
                        levelNum++;
                    }
                } else {
                    //回车，ASCII码13
                    //换行，ASCII码10
                    //空格，ASCII码32
                    Map<String, byte[]> header = request.getHeader();
                    if (array[i] == 58) {
                        byte[] bytes = new byte[i - start];
                        center = i;
                        System.arraycopy(array, start, bytes, 0, bytes.length);
                        key = new String(bytes);
                    }
                    //解析 请求头
                    if (isLine(array[i], array[i + 1])) {
                        //
                        values = new byte[i - center];
                        System.arraycopy(array, center + 1, values, 0, values.length);
                        start = i + 2;
                        header.put(key, values);
                    }
                }
            }
            byte[] bytes = null;
            if (request.getPath().startsWith(instance.getLocation())) {
                System.out.println("path=" + request.getPath());
                File file1 = null;
                if (instance.getAlias() == null) {
                    file1 = new File(System.getProperty("user.dir") + request.getPath());
                } else {
                    file1 = new File(instance.getAlias() + request.getPath());
                }

                if (file1.exists()) {
                    bytes = new byte[(int) file1.length()];
                    try (InputStream fis = new BufferedInputStream(new FileInputStream(file1))) {
                        int record = 0;
                        while ((record = fis.read(bytes)) != -1) {
                        }
                    }
                } else {
                    status = "404";
                }
            }
            response.setContext(bytes);
            response.setGetStatus(status);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("请求路径为：" + request.getPath());
        }
        return response;
    }


}
