package com.jinblog.shorturl.entry;

import com.jinblog.shorturl.common.EventEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    @Test
    void getEvent() {
        String u1 = "u1";
        Event event = new Event(EventEnum.ADD_EVENT, u1);
        assertEquals(EventEnum.ADD_EVENT, event.getEvent());
    }

    @Test
    void getKey() {
        String u1 = "u1";
        Event event = new Event(EventEnum.ADD_EVENT, u1);
        assertEquals(u1, event.getKey());
    }

    @Test
    void testToString() {
        String u1 = "u1";
        Event event = new Event(EventEnum.ADD_EVENT, u1);
        assertEquals(String.class, event.toString().getClass());
    }
}