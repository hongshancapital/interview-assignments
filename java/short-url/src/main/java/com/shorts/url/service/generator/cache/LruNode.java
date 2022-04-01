package com.shorts.url.service.generator.cache;

/**
 * <p>
 * LRU 双向链表节点
 * </p>
 *
 * @author WangYue
 * @date 2022/3/24 10:20
 */
public class LruNode {

    String key;

    /**
     * 节点值
     */
    String value;

    /**
     * 前节点
     */
    LruNode prev;

    /**
     * 下一个节点
     */
    LruNode next;

    public LruNode() {
    }

    public LruNode(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
