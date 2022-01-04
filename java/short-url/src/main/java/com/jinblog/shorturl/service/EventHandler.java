package com.jinblog.shorturl.service;

import com.jinblog.shorturl.entry.Event;

public interface EventHandler {
    public void handler(Event event);
}
