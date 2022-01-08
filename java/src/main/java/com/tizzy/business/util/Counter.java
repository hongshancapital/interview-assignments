package com.tizzy.business.util;

import java.util.concurrent.atomic.AtomicLong;

public class Counter {

    private static AtomicLong counter = new AtomicLong(0);

    public static long getOneCode(){
        return counter.incrementAndGet();
    }
}
