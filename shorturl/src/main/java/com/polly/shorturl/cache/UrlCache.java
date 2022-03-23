package com.polly.shorturl.cache;

import com.polly.shorturl.entity.ShortUrl;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author polly
 * @date 2022.03.20 10:59:32
 */
public class UrlCache {
    private final double THRESHOLD_RATE = 0.15;
    private final double THRESHOLD_MEMORY = 1024 * 1024 * 1024;
    private final Map<String, Node> map;
    private Node head;
    private Node tail;

    public UrlCache() {
        map = new HashMap<>();
    }

    public void memoryClean() {
        if (CollectionUtils.isEmpty(map)) {
            return;
        }
        long total = Runtime.getRuntime().totalMemory();
        long free = Runtime.getRuntime().freeMemory();
        double rate = (double) free / (double) total;
        if (rate < THRESHOLD_RATE || free <= THRESHOLD_MEMORY) {
            for (int i = 0; i < 5; i++) {
                Node delHead = removeHead();
                if (delHead != null) {
                    map.remove(delHead.k);
                }
            }
        }
    }

    /**
     * 添加元素
     * 1.元素存在，将元素移动到队尾
     * 2.不存在，判断链表是否满。
     * 如果满，则删除队首元素，放入队尾元素，删除更新哈希表
     * 如果不满，放入队尾元素，更新哈希表
     */
    public synchronized void put(String key, ShortUrl value) {
        Node node = map.get(key);
        if (node != null) {
            //更新值
            node.v = value;
            moveNodeToTail(node);
            return;
        }
        Node newNode = new Node(key, value);
        //链表满，需要删除首节点
        memoryClean();
        addLast(newNode);
        map.put(key, newNode);
    }

    public ShortUrl get(String key) {
        Node node = map.get(key);
        if (node != null) {
            synchronized (node) {
                moveNodeToTail(node);
            }
            return node.v;
        }
        return null;
    }

    public void addLast(Node newNode) {
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

    public void moveNodeToTail(Node node) {
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

    public Node removeHead() {
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

    /**
     * 判断url是否已存在
     *
     * @return
     */
    public boolean containsUrl(String shortUrl) {
        return map.containsKey(shortUrl);
    }

    class Node {
        String k;
        ShortUrl v;
        Node pre;
        Node next;

        Node(String k, ShortUrl v) {
            this.k = k;
            this.v = v;
        }
    }
}