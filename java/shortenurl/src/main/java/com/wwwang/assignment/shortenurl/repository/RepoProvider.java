package com.wwwang.assignment.shortenurl.repository;

/**
 * 域名仓库的统一接口，可以有不同实现
 * @param <K> key
 * @param <V> value
 */
public interface RepoProvider<K,V> {

    V get(K key);

    void put(K key,V value);

}
