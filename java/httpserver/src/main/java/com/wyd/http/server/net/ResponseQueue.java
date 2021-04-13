package com.wyd.http.server.net;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public final class ResponseQueue {

    private static final ResponseQueue responseQueue = new ResponseQueue();

    private final Queue<ResponseEvent> queue = new LinkedBlockingQueue();


    private ResponseQueue() {

    }

    public static ResponseQueue getResponseQueue() {
        return responseQueue;
    }

    public Queue getQueue() {
        return queue;
    }

    public void add(ResponseEvent event) {
        queue.add(event);
    }


}
