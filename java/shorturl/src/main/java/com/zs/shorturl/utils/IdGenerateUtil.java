package com.zs.shorturl.utils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 发号器
 *
 * @author zs
 * @date 2021/5/10
 */
public class IdGenerateUtil {

    /**
     * 模拟自增id
     */
    private static AtomicLong id = new AtomicLong(10000000L);


    /**
     * 增量
     */
    private static final long INCREMENT = 1L;



    /**
     *  获取自增id
     * @return
     */
    public static long generate(){
        return id.getAndAdd(INCREMENT);
    }





}
