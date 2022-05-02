package com.example.shortUrl.dao;

import org.springframework.util.CollectionUtils;

import java.text.CollationElementIterator;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author HOPE
 * @Description 带过期时间的缓存map
 * @Date 2022/5/2 12:34
 */
public class CacheMap {
    //通过持有一个map变量的方式，而不是采用继承，通过这种组合的方式使方法的定义更灵活
    private transient Map map;

    //构造函数私有化，不允许直接new
    private CacheMap(){
        //初始化时指定初始容量，避免反复扩容
        this.map = new ConcurrentHashMap<String,CacheValue>(1024);
        Timer timer=new Timer(true);
        //每隔30s检查一次，删除过期key-value
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                while(true){
                    if(!CollectionUtils.isEmpty(map)){
                        for(Iterator<Map.Entry<String,CacheValue>> it=map.entrySet().iterator();it.hasNext();){
                            Map.Entry<String,CacheValue> item = it.next();
                            if(item.getValue().getExpireTime() <= System.currentTimeMillis()){
                                it.remove();
                            }
                        }
                    }
                }
            }
        },30*1000,30*1000);
    }
    public Object getValue(String key){
        CacheValue cacheValue = (CacheValue)map.get(key);
        if(cacheValue!=null&&cacheValue.getExpireTime()>System.currentTimeMillis()){
              return cacheValue.getValue();
        }else if(cacheValue!=null&&map.containsKey(key)){
            //清理数据
            map.remove(key);
        }
        return null;
    }
    //默认单位是秒
    public void setEx(String key,Object value,long times){
        CacheValue cacheValue = new CacheValue(value,System.currentTimeMillis()+times*1000);
        map.put(key,cacheValue);

    }

    public boolean containsKey(String key){
        return map.containsKey(key);
    }

    public void clear(){
        map.clear();
    }

    class CacheValue{
        public Object value;
        public long expireTime;

        public CacheValue(Object value, long expireTime) {
            this.value = value;
            this.expireTime = expireTime;
        }

        public CacheValue() {
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public long getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(long expireTime) {
            this.expireTime = expireTime;
        }
    }



}

