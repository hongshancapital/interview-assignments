package com.example.demo.service;

import com.example.demo.model.URL;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LRUCache{
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private long size;
    private Map<Long, Node> map;
    private Map<String, Long> index;
    private Node head;
    private Node tail;

    LRUCache(long size) {
        this.size = size;
        map = new HashMap<>();
        index = new HashMap<>();
    }

    /**
     * 添加元素
     * 1.元素存在，将元素移动到队尾
     * 2.不存在，判断链表是否满。
     * 如果满，则删除队首元素，放入队尾元素，删除更新哈希表
     * 如果不满，放入队尾元素，更新哈希表
     */
    public void put(Long id, URL url) {
        lock.writeLock().lock();
        try {
            Node node = map.get(id);
            if (node != null) {
                //更新值
                node.url = url;
                moveNodeToTail(node);
            } else {
                Node newNode = new Node(id, url);
                //链表满，需要删除首节点
                if (map.size() == size) {
                    Node delHead = removeHead();
                    map.remove(delHead.id);
                    index.remove(delHead.url.getOriginalURL());
                }
                addLast(newNode);
                map.put(id, newNode);
                index.put(newNode.url.getOriginalURL(), id);
            }
        }finally {
            lock.writeLock().unlock();
        }
    }

    public URL get(Long id) {
        lock.readLock().lock();
        try {
            Node node = map.get(id);
            if (node != null) {
                moveNodeToTail(node);
                return node.url;
            }
            return null;
        }finally {
            lock.readLock().unlock();
        }
    }

    public Long getIdByOriginalURL(String originalURL){
        lock.readLock().lock();
        try {
            Long id = Long.MIN_VALUE;
            if(index.get(originalURL) != null){
                id = index.get(originalURL);
            }
            return id;
        }finally {
            lock.readLock().unlock();
        }
    }

    private void addLast(Node newNode) {
        if (newNode == null) {
            return;
        }
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            //连接新节点
            tail.next = newNode;
            newNode.pre = tail;
            //更新尾节点指针为新节点
            tail = newNode;
        }
    }

    private void moveNodeToTail(Node node) {
        if (tail == node) {
            return;
        }
        if (head == node) {
            head = node.next;
            head.pre = null;
        } else {
            //调整双向链表指针
            node.pre.next = node.next;
            node.next.pre = node.pre;
        }
        node.pre = tail;
        node.next = null;
        tail.next = node;
        tail = node;
    }

    private Node removeHead() {
        if (head == null) {
            return null;
        }
        Node res = head;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            head = res.next;
            head.pre = null;
            res.next = null;
        }
        return res;
    }

    class Node {
        Long id;
        URL url;
        Node pre;
        Node next;

        Node(Long id, URL url) {
            this.id = id;
            this.url = url;
        }
    }
}
