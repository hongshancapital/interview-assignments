package com.jinblog.shorturl.service.impl;

import com.jinblog.shorturl.common.UrlUtil;
import com.jinblog.shorturl.config.ShortConfiguration;
import com.jinblog.shorturl.entry.Event;
import com.jinblog.shorturl.service.RecyclingStrategy;
import com.jinblog.shorturl.service.EventHandler;
import com.jinblog.shorturl.service.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EventHandlerImpl implements EventHandler {
    @Autowired
    RecyclingStrategy recyclingStrategy;

    @Autowired
    Storage storage;

    @Autowired
    ShortConfiguration shortConfiguration;

    @Override
    public void handler(Event event) {
        switch (event.getEvent()) {
            case ADD_EVENT:
                recyclingStrategy.handleAddEvent(event.getKey());
                if (UrlUtil.usedMemoryPercent() >= shortConfiguration.getStartRecyclingStrategyMemoryPercent()) {
                    log.debug("Start GC. Lunched by add event");
                    gc();
                }
                break;
            case GET_EVENT:
                recyclingStrategy.handleGetEvent(event.getKey());
                break;
            case GC_EVENT:
                gc();
                break;
        }
    }

    public void gc() {
        double usedMemoryPercent;
        String garbage;
        do {
            garbage = recyclingStrategy.popGarbage();
            if (garbage == null) break;
            storage.delete(garbage);
            usedMemoryPercent = UrlUtil.usedMemoryPercent();
        } while (usedMemoryPercent >= shortConfiguration.getStartRecyclingStrategyMemoryPercent());
        log.debug("End GC");
    }

    @Override
    public String toString() {
        return "EventHandlerImpl{" +
                "recyclingStrategy=" + recyclingStrategy +
                "}";
    }
}
