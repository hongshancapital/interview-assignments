package com.example.tinyurl.util;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhangtian
 * @version 1.0
 * @Project: LRU
 * @Title: tinyurl
 * @Package com.example.tinyurl.util
 * @Description:缓存类，LRU算法实现
 * @date 2021/12/19 18:39
 */
public class LRU {
    /**
     * 当前的大小
     */
    private AtomicInteger currentSize;
    /**
     * 总容量
     */
    private int capcity;
    /**
     * 所有的node节点
     */
    private ConcurrentHashMap<String, Node> caches;

    private Node first;
    private Node last;

    private LRU() {
        currentSize = new AtomicInteger(0);
        /**
         * 按每条数据100B算，总占用内存在1G左右，超过的数据被淘汰
         */
        capcity = (int)(Math.pow(2,20));
        caches = new ConcurrentHashMap<String, Node>(capcity);
    }

    /**
     * 单例模式，保证只有一块缓存区域
     */
    private static class LRUSingleton{
        private static final LRU singleton = new LRU();
    }

    public static LRU getInstance() {
        return LRUSingleton.singleton;
    }

    /**
     * 放入元素
     * @param key 短链接
     * @param value 长链接
     */
    public synchronized void put(String key, String value) {
        Node node = caches.get(key);
        if (node == null) {
            //如果超过元素容纳量，移除最后一个节点
            if (caches.size() >= capcity) {
                removeLast();
            }
            node = new Node(key,value);
            currentSize.incrementAndGet();
        }
        //已经存在的元素覆盖旧值
        node.value = value;
        //把元素移动到首部
        moveToHead(node);
        caches.put(key, node);
    }

    /**
     * 通过短链接获取长链接
     * @param key 短链接
     * @return 长链接
     */
    public String get(String key) {
        Node node = caches.get(key);
        if(node != null) {
            //把访问的节点移动到首部
            moveToHead(node);
            return node.value;
        } else {
            return null;
        }
    }
 
    /**
     * 把当前节点移动到首部
     * @param node
     */
    private void moveToHead(Node node) {
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
     * 移除最后一个节点
     */
    private synchronized void removeLast() {
        if (caches.size() >= capcity) {
            caches.remove(last.key);
        }
        if (last != null) {
            last = last.pre;
            if (last == null) {
                first = null;
            } else {
                last.next = null;
            }
        }

        currentSize.decrementAndGet();
    }

    public int getCurrentSize() {
        return currentSize.get();
    }
}

class Node {
    String key;
    String value;
    Node pre;
    Node next;

    public Node(String key, String value) {
        this.key = key;
        this.value = value;
    }
}