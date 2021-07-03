package com.task.utils.inter;

import org.springframework.stereotype.Component;

import java.util.Set;

public interface MyMap<K,V> {

    /**
     * @Description: 插入键值对方法
     * @param k
     * @param v
     * @return
     */
    public V put(K k,V v);
    /**
     * @Description:根据key获取value
     * @param k
     * @return
     */
    public V get(K k);

    /**
     * @Description: 判断key键是否存在
     * @param k  key键
     * @return
     */
    public boolean containsKey(K k);


    /**
     * @Description: 获取map集合中所有的key，并放入set集合中
     * @return
     */
    public Set<K> keySet();





//------------------------------内部接口 Entry（存放key-value）---------------------

    /**
     * @Title: Enter
     * @Description: 定义内部接口 Entry，存放键值对的Entery接口
     */
    interface Entry<K,V>{
        /**
         * @Description: 获取key方法
         * @return
         */
        public K getKey();
        /**
         * @Description:获取value方法
         * @return
         */
        public V getValue();
    }

}

