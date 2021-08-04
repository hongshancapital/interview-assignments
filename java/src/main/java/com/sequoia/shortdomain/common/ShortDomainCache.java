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
    public  void addCache(String key ,String value){
        if(chm.size()==this.shortDomainCacheMax){
           this.clearCache();
        }
        chm.put(key.intern(), value);
        lruList.add(key);
    }

    public  String getCache(String key){
        if(lruList.contains(key.intern())){
            lruList.remove(key.intern());
            lruList.add(key);
        }
        return chm.get(key);
    }

    public  void clearCache(){
        for(int i=0;i<this.shortDomainCacheClearNum;i++){
            chm.remove(lruList.get(i));
        }
    }
}
