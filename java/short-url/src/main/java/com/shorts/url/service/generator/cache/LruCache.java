package com.shorts.url.service.generator.cache;

import lombok.Data;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>
 * LRU 缓存
 * Map + 双向链表 实现
 * </p>
 *
 * @author WangYue
 * @date 2022/3/24 10:20
 */
@Data
public class LruCache {

    /**
     * 缓存容器，存储数据
     * key
     * value -> lruNode
     */
    private ConcurrentHashMap<String, LruNode> cache = new ConcurrentHashMap<>();

    /**
     * 缓存存储的数量,当大于capacity时，淘汰最久未使用的
     */
    private AtomicLong count;

    /**
     * 缓存容量
     */
    private long capacity;

    /**
     * 头结点
     */
    private LruNode head;

    /**
     * 尾节点
     */
    private LruNode tail;

    public LruCache(long capacity) {
        this.count = new AtomicLong(0);
        this.capacity = capacity;

        head = new LruNode();
        head.prev = null;

        tail = new LruNode();
        tail.next = null;

        tail.prev = head;
        head.next = tail;
    }

    /**
     * 1. key不存在
     *     1.1 新建node,并添加到头节点
     *     1.2 若缓存已满，删除尾节点
     * 2. key已存在
     *     2.1 修改value值，并移动到尾节点
     * @param key
     * @param value
     */
    public void put(String key, String value) {
        LruNode current = cache.get(key);
        if (null == current) {
            // 新建node
            current = new LruNode(key, value);

            // 移动到头节点
            addNode(current);
            cache.put(key, current);

            // 新增数量
            if (count.incrementAndGet() > capacity) {
                // 移除尾节点
                LruNode tailNode = removeTail();
                // 删除cache
                cache.remove(tailNode.key);
                count.decrementAndGet();
            }
        } else {
            // 移动到头节点
            current.value = value;
            moveToHead(current);
        }
    }

    /**
     * key已存在，移动到头节点
     * @param key
     */
    public String get(String key) {
        LruNode current = cache.get(key);
        if (null == current) {
            return null;
        }
        // 移动到头节点
        moveToHead(current);
        return current.value;
    }

    /**
     * 新增节点，新增的节点都加入到head的next
     * @param node
     */
    private void addNode(LruNode node) {
        node.prev = head;
        node.next = head.next;

        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(LruNode node) {
        LruNode prevNode = node.prev;
        LruNode nextNode = node.next;

        prevNode.next = nextNode;
        nextNode.prev = prevNode;

        node.prev = null;
        node.next = null;
    }

    private void moveToHead(LruNode node) {
        removeNode(node);
        addNode(node);
    }

    private LruNode removeTail() {
        LruNode tailNode = tail.prev;
        removeNode(tailNode);
        return tailNode;
    }
}
