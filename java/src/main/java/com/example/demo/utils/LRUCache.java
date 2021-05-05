package com.example.demo.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * LRU缓存，采用双链表+哈希表
 * <p>
 * setCache 设置缓存
 * <p>
 * 1.如果缓存存在，就先删除在更新并放入首节点;
 * <p>
 * 2.如果缓存不存在，放入链表的首节点;
 * <p>
 * 3.当容量超出之后，删除链表末尾的节点;
 * <p>
 * getCache 得到缓存，
 * <p>
 * 1.先删除，再放到链表的首节点;
 */
public class LRUCache {

    /**
     * 链表节点的定义
     */
    class LRUNode {
        String key;
        Object value;
        LRUNode next;
        LRUNode pre;

        public LRUNode(String key, Object value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * 存储数据的map
     */
    private Map<String, LRUNode> map = new HashMap<>();
    /**
     * 头节点
     */
    private LRUNode head;
    /**
     * 尾结点
     */
    private LRUNode tail;
    /**
     * 节点的容量
     */
    private int capacity;

    public LRUCache(int capacity) {
        /**
         * 缓存容量不能为空
         */
        if (capacity <= 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + capacity);
        }
        this.capacity = capacity;
    }

    /**
     * 设置缓存
     *
     * @param key   缓存key
     * @param value 缓存value
     */
    public synchronized void setCache(String key, Object value) {
        if (head == null) {
            head = new LRUNode(key, value);
            tail = head;
            map.put(key, head);
        }
        LRUNode node = map.get(key);
        if (node != null) {
            // 更新值
            node.value = value;
            // 把他从链表删除并且插入到头结点
            removeAndInsert(node);
        } else {
            node = new LRUNode(key, value);
            // 如果会溢出
            if (map.size() >= capacity) {
                // 先把它从哈希表中删除
                map.remove(tail.key);
                // 删除尾部节点
                tail = tail.pre;
                tail.next = null;
            }
            map.put(key, node);
            // 放到链表首节点
            node.next = head;
            head.pre = node;
            head = node;
        }
    }

    /**
     * 得到缓存
     *
     * @param key 缓存key
     * @return
     */
    public synchronized Object getCache(String key) {
        LRUNode node = map.get(key);
        if (node != null) {
            // 把这个节点删除并插入到头结点
            removeAndInsert(node);
            return node.value;
        }
        return null;
    }

    /**
     * 删除缓存
     *
     * @param key 缓存key
     * @return
     */
    public synchronized Object delCache(String key) {
        LRUNode node = map.get(key);
        if (node != null) {
            // 把这个节点删除并插入到头结点
            if (node == head) {
                head = node.next;
                if (head != null) {
                    head.pre = null;
                }
            } else if (node == tail) {
                tail = node.pre;
                tail.next = null;
            } else {
                node.pre.next = node.next;
                node.next.pre = node.pre;
            }
            return node.value;
        }
        return null;
    }

    /**
     * 删除缓存，再插入
     *
     * @param node
     */
    private void removeAndInsert(LRUNode node) {
        // 特殊情况先判断，例如该节点是头结点或是尾部节点
        if (node == head) {
            return;
        } else if (node == tail) {
            tail = node.pre;
            tail.next = null;
        } else {
            node.pre.next = node.next;
            node.next.pre = node.pre;
        }
        // 插入到头结点
        node.next = head;
        node.pre = null;
        head.pre = node;
        head = node;
    }
}
