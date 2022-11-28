package com.jinblog.shorturl.entry;

import com.jinblog.shorturl.service.EventHandler;

/**
 * 任务对象，用于封装事件的Runnable
 */
public class Task implements Runnable {
    public Event event;
    public EventHandler eventHandler;
    public Task(Event event, EventHandler eventHandler) {
        this.event = event;
        this.eventHandler = eventHandler;
    }

    @Override
    public void run() {
        eventHandler.handler(event);
    }

    @Override
    public String toString() {
        return "Task{" +
                "event=" + event +
                ", eventHandler=" + eventHandler +
                '}';
    }
}
