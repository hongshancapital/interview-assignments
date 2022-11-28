package com.jinblog.shorturl.service.impl;

import com.jinblog.shorturl.entry.LRUNode;
import com.jinblog.shorturl.service.RecyclingStrategy;
import com.jinblog.shorturl.service.Storage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * 删除最久未使用的
 */
public class RecyclingStrategyLRUImpl implements RecyclingStrategy {

    @Autowired
    Storage storage;

    LRUNode head, tail;
    Map<Object, LRUNode> map = new HashMap<>();
    public RecyclingStrategyLRUImpl() {
        head = new LRUNode(null);
        tail = new LRUNode(null);
        head.setNext(tail);
        tail.setPre(head);
    }

    /**
     * 响应增删改事件
     * @param key 发生事件的url
     */
    @Override
    public void handleAddEvent(String key) {
        LRUNode lruNode = new LRUNode(key);
        map.put(key, lruNode);
        moveToHead(lruNode);
    }

    @Override
    public void handleGetEvent(String key) {
        LRUNode lruNode = map.get(key);
        if (lruNode == null) {
            return;
        }
        remove(lruNode);
        moveToHead(lruNode);
    }

    public LRUNode remove(LRUNode node) {
        node.getPre().setNext(node.getNext());
        node.getNext().setPre(node.getPre());
        return node;
    }

    public void moveToHead(LRUNode node) {
        node.setPre(head);
        node.setNext(head.getNext());
        head.getNext().setPre(node);
        head.setNext(node);
    }

    @Override
    public String popGarbage() {
        if (tail.getPre() == head) return null;
        return remove(tail.getPre()).getShortUrl();
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }
}
