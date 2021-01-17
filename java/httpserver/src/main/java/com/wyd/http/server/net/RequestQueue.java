package com.wyd.http.server.net;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public final class RequestQueue {

    private static final RequestQueue requestQueue = new RequestQueue();

    private final Queue<RequestEvent> queue = new LinkedBlockingQueue();


    private RequestQueue() {

    }

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public Queue getQueue() {
        return queue;
    }

    public void add(RequestEvent event) {
        queue.add(event);
    }


}
