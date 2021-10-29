package com.scdt.utils;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicLongTest {
    public static void main(String[] args) {
        AtomicLong atomicLong_5 = new AtomicLong();


        atomicLong_5.set(1);
        System.out.println(atomicLong_5.addAndGet(5));
    }
}
