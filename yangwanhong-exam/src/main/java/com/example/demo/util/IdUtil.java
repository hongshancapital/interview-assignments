package com.example.demo.util;

import java.util.concurrent.atomic.AtomicLong;

public class IdUtil {

    private static AtomicLong GLOBAL_ID = new AtomicLong(13148023);

    public static Long getGlobalId(){
        return GLOBAL_ID.incrementAndGet();
    }
}
