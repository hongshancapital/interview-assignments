package com.wangxinyu.interview.model;

import java.util.concurrent.ConcurrentHashMap;

public class MemData {
    public static final ConcurrentHashMap<Long,String> DB = new ConcurrentHashMap<>();
}
