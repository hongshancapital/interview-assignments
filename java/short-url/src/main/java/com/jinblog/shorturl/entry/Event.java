package com.jinblog.shorturl.entry;

import com.jinblog.shorturl.common.EventEnum;

/**
 * 事件封装对象
 */
public class Event {
    public EventEnum event;
    public String key;
    public Event(EventEnum eventEnum, String shortUrl) {
        this.event = eventEnum;
        this.key = shortUrl;
    }

    public EventEnum getEvent() {
        return event;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "Event{" +
                "event=" + event +
                ", key='" + key + '\'' +
                '}';
    }
}
