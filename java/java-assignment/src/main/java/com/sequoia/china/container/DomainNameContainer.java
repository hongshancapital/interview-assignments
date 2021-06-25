package com.sequoia.china.container;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 域名容器
 * @Author helichao
 * @Date 2021/6/24 7:38 下午
 */
public class DomainNameContainer {

    /**
     * 存储key=长域名，value=短域名
     */
    public ConcurrentHashMap<String,String> longToShortMap=new ConcurrentHashMap();

    /**
     * 存储key=短域名，value=长域名
     */
    public ConcurrentHashMap<String,String> shortToLongMap=new ConcurrentHashMap();

    private DomainNameContainer(){}

    private final static DomainNameContainer INSTANCE=new DomainNameContainer();

    public static DomainNameContainer getInstance(){
        return INSTANCE;
    }


}
