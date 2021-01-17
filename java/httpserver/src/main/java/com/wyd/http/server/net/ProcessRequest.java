package com.wyd.http.server.net;

import com.wyd.http.server.HttpServerConfig;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ProcessRequest extends Thread {
    private ThreadPoolExecutor threadPoolExecutor;


    public ProcessRequest() {
        HttpServerConfig instance = HttpServerConfig.Sinalton.SINALTON.getInstance();
        threadPoolExecutor = new ThreadPoolExecutor(instance.getCoreSize(), instance.getMaxsize(),
                instance.getKeeplive(), TimeUnit.MILLISECONDS, instance.getQueue());

    }

    @Override
    public void run() {
        while (true) {
            ResponseQueue responseQueue = ResponseQueue.getResponseQueue();
            Queue<ResponseEvent> queue = responseQueue.getQueue();
            for (int i = 0; i < queue.size(); i++) {
                ResponseEvent event = queue.poll();
                SelectionKey selectionKey = event.getSelectionKey();
                SocketChannel socketChannel = event.getSocketChannel();
                Selector selector = event.getSelector();
                threadPoolExecutor.execute(() -> {
                    processResponse(socketChannel, selectionKey, selector);
                });
                event = null;
//                }
            }
        }
    }

    private void processResponse(SocketChannel channel, SelectionKey next, Selector selector) {
        try {
            Response attachment = (Response) next.attachment();
            String response = response(attachment);
            channel.write(ByteBuffer.wrap(response.getBytes()));
            channel.close();
            next.cancel();
            selector.wakeup();
            attachment=null;
            response=null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static String response(Response response) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("HTTP/1.1").append(" ").append(response.getGetStatus()).append(" ").append(response.getStatusFlag()).append("\r\n")
                .append("Content-Type: text/plain;charset=UTF-8 ").append("\r\n")
                .append("Connection: keep-alive").append("\r\n")
                .append("accept-ranges: bytes").append("\r\n")
                .append("Content-Length: ").append(response.getContentLength()).append(" ").append("\r\n")
                .append("\r\n")
                .append(response.getContext());
        String s = stringBuilder.toString();
        return s;
    }
}
