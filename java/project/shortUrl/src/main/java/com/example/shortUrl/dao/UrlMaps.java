package com.example.shortUrl.dao;

import org.apache.commons.collections4.map.LRUMap;


/**
 * @Author HOPE
 * @Description 长短链接映射存储容器,用集合来模拟存储
 * @Date 2022/4/28 15:48
 */
public class UrlMaps {
    //为防止内存溢出，使用带回收策略的map集合
    public static LRUMap<String,String> shortUrls = new LRUMap<String,String>((int)Math.pow(2,20));
    public static LRUMap<String,String> md5Urls = new LRUMap<String,String>((int)Math.pow(2,20));

}

