package com.sequoia.shortdomain.common;

import com.sequoia.shortdomain.config.CommonConfig;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ShortDomainCache {

    private int shortDomainCacheMax;
    private int shortDomainCacheClearNum;

    private  ConcurrentHashMap<String, String> chm ;
    /**
     * 实现lru模式
     */
    private static final List<String> lruList = new LinkedList<>();
    private final static Object syncLock = new Object();
    private final static Object cacheLock = new Object();
    public static ShortDomainCache shortDomainCache;
    LinkedHashMap map=new LinkedHashMap();
    private ShortDomainCache(){

    }
    private ShortDomainCache(int shortDomainCacheMax,int shortDomainCacheMin,int shortDomainCacheClearNum){
             this.shortDomainCacheMax=shortDomainCacheMax;
             this.shortDomainCacheClearNum=shortDomainCacheClearNum;
             chm= new ConcurrentHashMap<>(shortDomainCacheMin);

    }

    /**
     * 初始化缓存,所以推荐是在系统启动的时候调用
     * @param shortDomainCacheMax
     * @param shortDomainCacheMin
     */
    public static void  init(int shortDomainCacheMax,int shortDomainCacheMin,int shortDomainCacheClearNum){

        if(shortDomainCache==null){
            synchronized(syncLock){
                if(shortDomainCache==null){
                    shortDomainCache=new ShortDomainCache(shortDomainCacheMax,shortDomainCacheMin,shortDomainCacheClearNum);
                }

            }
        }

    }

    /**
     * 应先调用init,初始化单例，之所以需要先调用init，是因为需要初始化缓存
     * @return
     */
    public static ShortDomainCache getShortDomainCache(){
        Objects.isNull(shortDomainCache);
        return shortDomainCache;
    }

    /**
     * 增加缓存
     * @param key
     * @param value
     */
    public  void addCache(String key ,String value){
        //此处应该加锁，防止高并发的情况下，缓存的容量限制无效
        synchronized(syncLock){
            if(chm.size()==this.shortDomainCacheMax){
                this.clearCacheFirst();
            }
        }

        chm.put(key.intern(), value);
        if(!lruList.contains(key.intern())){
            lruList.add(key);
        }

    }

    /**
     * 根据key获取缓存数据
     * @param key
     * @return
     */
    public  String getCache(String key){
        if(lruList.contains(key.intern())){
            lruList.remove(key.intern());
            lruList.add(key.intern());
        }
        return chm.get(key.intern());
    }

    /**
     * 用于定时清理缓存，链表最上头的为最近很少使用的
     */
    public  void clearCache(){
        if(chm.size()<=0){
            return;
        }
        for(int i=0;i<this.shortDomainCacheClearNum;i++){
            chm.remove(lruList.get(i));
        }
    }

    /**
     * 当添加缓存数据时，如果满了，则调用此方法，清理缓存，只清理一个最近很少使用的
     */
    public  void clearCacheFirst(){
        chm.remove(lruList.get(0));
    }
}
