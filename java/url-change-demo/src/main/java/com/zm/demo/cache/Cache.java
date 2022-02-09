package com.zm.demo.cache;

/**
 * @ClassName Cache
 * @Description TODO
 * @Author zhaomin
 * @Date 2021/10/29 17:33
 **/
public interface Cache <K, V>{

    public void put(K k,V v);

    public V get(K k);
}
