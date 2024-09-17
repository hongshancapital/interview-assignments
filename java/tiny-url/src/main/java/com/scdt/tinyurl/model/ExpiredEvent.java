package com.scdt.tinyurl.model;

import org.springframework.context.ApplicationEvent;

public class ExpiredEvent extends ApplicationEvent {

    private Object expiredEvent;

    public ExpiredEvent(Object source) {
        super(source);
        this.expiredEvent = source;
    }
}
