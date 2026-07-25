package com.zh.shortUrl.util;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Author hang.zhang
 * @Date 2022/1/18 15:11
 * @Version 1.0
 * 提供统一短链生成读取和内存维护
 */
@Component
public class ShortUrlUtils {
    /**
     * 用于存储已匹配的长短连接对，短连接为key 长链接为value 这玩意自带线程安全
     */
    private static final ConcurrentHashMap<String,String> urlMap = new ConcurrentHashMap<>();

    /**
     * 用于存储已匹配的长短连接对，长连接为key 短链接为value 这玩意自带线程安全
     */
    private static final ConcurrentHashMap<String,String> longUrlMap = new ConcurrentHashMap<>();
    /**
     * 用于预存已生成的短连接，由于本生成算法与实际长连接无逻辑关联，预先生成后直接使用可提并发能力  这玩意也自带线程安全
     */
    private static final Queue<String> shortUrlQueue = new LinkedBlockingDeque<>(1000000);

    /**
     * 用于存放已使用的短链 便于回收
     */
    private static final Queue<String> unUseUrlQueue = new LinkedBlockingDeque<>(1000000);

    /**
     * 计数，用于唯一短链接生成 分布式环境下可以使用redis自增，顺便还能解决并发问题 单机条件下单线程生成不存在并发问题
     * 单机下还有个问题是服务重启这个数字怎么持久化 可以考虑定时写到项目某个配置文件里面此处不具体实现
     */
    private static long index = 1;

    private static final String[]  allChar ={
            "a" , "b" , "c" , "d" , "e" , "f" , "g" , "h" ,
            "i" , "j" , "k" , "l" , "m" , "n" , "o" , "p" , "q" , "r" , "s" , "t" ,
            "u" , "v" , "w" , "x" , "y" , "z" , "0" , "1" , "2" , "3" , "4" , "5" ,
            "6" , "7" , "8" , "9" , "A" , "B" , "C" , "D" , "E" , "F" , "G" , "H" ,
            "I" , "J" , "K" , "L" , "M" , "N" , "O" , "P" , "Q" , "R" , "S" , "T" ,
            "U" , "V" , "W" , "X" , "Y" , "Z"
    };

    private static final int charLength = 62;

    static{
        addShortUrl();
    }

    /**
     * 不考虑对齐啥的 62^8个
     * @return
     */
    private static String decShortUrl(){
        long num = index;
        int temp;
        String shortUrl="";
        while(num!=0){
            temp =(int) num%charLength;
            shortUrl+=allChar[temp];
            num = num/charLength;
        }
        index++;
        return shortUrl;
    }

    /**
     * 一条线程专门添加短链
     */
    private static void addShortUrl() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    //这里顺便清理一下内存，正常来讲再起一条线程比较合理
                    if(unUseUrlQueue.size()>100000){
                        //已生成十万条 则清理掉最先生成的一万条 也建议做成可配置
                        String shortUrl;
                        String longUrl;
                        for(int i =0;i<10000;i++){
                            shortUrl = unUseUrlQueue.poll();
                            longUrl = urlMap.get(shortUrl);
                            urlMap.remove(shortUrl);
                            longUrlMap.remove(longUrl);
                        }
                    }

                    //这个建议做成可配置
                    if (shortUrlQueue.size() < 10000) {
                        //添加和抽取数据都会锁整个队列，大量并发情况下可能会导致频繁的锁争夺开销，所以用一个额外的队列操作
                        //这里是稳定的单线程环境
                        //ps 如果使用redis的话可以基本0开发量解决并发，内存回收等等问题
                        LinkedList<String> tempList = new LinkedList<>();
                        for(int i =0;i<100000;i++){
                            tempList.add(decShortUrl());
                        }
                        shortUrlQueue.addAll(tempList);
                    } else {
                        try {
                            Thread.sleep(20L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }){
        }.start();
    }

    /**
     * 获取短链并配对
     * @param longUrl
     * @return
     */
    public String getShortUrl(String longUrl){
        if(null!=longUrlMap.get(longUrl)){
            return longUrlMap.get(longUrl);
        }else {
            String shortUrl = shortUrlQueue.poll();
            //防内存溢出可以在这里加一个链表放弹出来的短链，然后超过长度限制之后删掉最先进队的短链
            unUseUrlQueue.add(shortUrl);
            urlMap.put(shortUrl, longUrl);
            longUrlMap.put(longUrl,shortUrl);
            return shortUrl;
        }
    }

    /**
     * 根据短链获取长链
     */
    public String getLongUrl(String shortUrl){
        return urlMap.get(shortUrl);
    }


}
