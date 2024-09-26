package com.jk.shorturl.Utils;

import java.util.HashMap;

/**
 * 基于HashMap实现的自定义LRU算法，实现固定长度的 双向链表 容器，当容量达到最大容量后，首先淘汰最长时间未被使用的元素
 * 初始化的时候需要指定集合的最大容量。
 *
 * @param <K> key的类型
 * @param <V> value的类型
 */
public class LRUCache<K, V> {
    private int currentCacheSize;
    private int CacheCapcity;
    private HashMap<K, CacheNode> caches;
    private CacheNode first;
    private CacheNode last;

    /**
     * 构造函数，初始化一个 固定长度的 map 容器
     *
     * @param size 最大长度，即最多能够存储的元素个数。
     */
    public LRUCache(int size) {
        currentCacheSize = 0;
        this.CacheCapcity = size;
        caches = new HashMap<K, CacheNode>(size);
    }

    /**
     * @return 当前集合最大容量
     */
    public int getMaxSize() {
        return CacheCapcity;
    }

    /**
     * @return 当然集合实际存储的元素个数
     */
    public int getCurrentSize() {
        return currentCacheSize;
    }

    /**
     * 向容器添加元素。如果元素存在，则执行更新和移动操作，即更新value值，同时将此节点放到容器的头部。
     * 如果元素不存在，则新增加一个节点，如果容量超过最大容量，则删除最后一个节点，然后执行上述的元素更新和移动操作。
     *
     * @param k key值
     * @param v value值
     */
    public void put(K k, V v) {
        CacheNode node = caches.get(k);
        if (node == null) {
            if (caches.size() >= CacheCapcity) {
                caches.remove(last.key);
                removeLast();
            }
            node = new CacheNode();
            node.key = k;
        }
        node.value = v;
        moveToFirst(node);
        currentCacheSize++;
        caches.put(k, node);
    }

    /**
     * 根据key值获取某个元素的value，如果此元素存在，则将此元素移动到头部；
     *
     * @param k key值
     * @return 返回对应的value值，如果不存在则返回 null
     */
    public V get(K k) {
        CacheNode node = caches.get(k);
        if (node == null) {
            return null;
        }
        moveToFirst(node);
        return node.value;
    }

    /**
     * 移除某个元素
     *
     * @param k 要移除的元素的 key
     * @return 移除失败返回null，移除成功返回元素的值(如果该元素的值为null，则也是返回null)
     */
    public Object remove(K k) {
        CacheNode node = caches.get(k);
        if (node != null) {
            if (node.pre != null) {
                node.pre.next = node.next;
            }
            if (node.next != null) {
                node.next.pre = node.pre;
            }
            if (node == first) {
                first = node.next;
            }
            if (node == last) {
                last = node.pre;
            }
        }
        currentCacheSize--;

        CacheNode e;
        return (e = caches.remove(k)) == null ? null : e.value;
    }

    /**
     * 清空容器
     */
    public void clear() {
        first = null;
        last = null;

        currentCacheSize = 0;
        caches.clear();
    }

    /**
     * 将元素移动到链表的头部
     *
     * @param node
     */
    private void moveToFirst(CacheNode node) {
        if (first == node) {
            return;
        }
        if (node.next != null) {
            node.next.pre = node.pre;
        }
        if (node.pre != null) {
            node.pre.next = node.next;
        }
        if (node == last) {
            last = last.pre;
        }
        if (first == null || last == null) {
            first = last = node;
            return;
        }

        node.next = first;
        first.pre = node;
        first = node;
        first.pre = null;

    }

    /**
     * 移除尾部的元素，私有方法，执行自动删除策略的时候使用
     */
    private void removeLast() {
        if (last != null) {
            last = last.pre;
            if (last == null) {
                first = null;
            } else {
                last.next = null;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        CacheNode node = first;
        while (node != null) {
            sb.append(String.format("%s:%s ", node.key, node.value));
            node = node.next;
        }

        return sb.toString();
    }

    /**
     * 双向链表的 Node 节点
     */
    class CacheNode {
        CacheNode pre;//前一个节点
        CacheNode next;//后一个节点
        K key;//节点的 key
        V value;//节点的 value

        public CacheNode() {

        }
    }
}
