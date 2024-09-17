package com.zhouhongbing.shorturl.utils;

import java.util.HashMap;

/**
 * @version 1.0
 * @Author 海纳百川zhb
 **/
public class LRUCache {
    private Node head;
    private Node end;
    //缓存存储上限
    private Long limit;

    private HashMap<String, Node> hashMap;

    public LRUCache(long limit) {
        this.limit = limit;
        hashMap = new HashMap<String, Node>();
    }

    public String get(String key) {
        Node node = hashMap.get(key);
        if (node == null) {
            return null;
        }
        refreshNode(node);
        return node.value;
    }

    public void put(String key, String value) {
        Node node = hashMap.get(key);
        if (node == null) {
            //如果Key不存在，则插入Key-Value
            if (hashMap.size() >= limit) {
                String oldKey = removeNode(head);
                hashMap.remove(oldKey);
            }
            node = new Node(key, value);
            addNode(node);
            hashMap.put(key, node);
        } else {
            //如果Key存在，则刷新Key-Value
            node.value = value;
            refreshNode(node);
        }
    }

    public void remove(String key) {
        Node node = hashMap.get(key);
        removeNode(node);
        hashMap.remove(key);
    }

    /**
     * 刷新被访问的节点位置
     *
     * @param node 被访问的节点
     */
    private void refreshNode(Node node) {
//如果访问的是尾节点，则无须移动节点
        if (node == end) {
            return;
        }
        //移除节点
        removeNode(node);
        //重新插入节点
        addNode(node);
    }

    /**
     * 删除节点
     *
     * @param node 要删除的节点
     */
    private String removeNode(Node node) {
        if (node == head && node == end) {
            //移除唯一的节点
            head = null;
            end = null;
        } else if (node == end) {
            //移除尾节点
            end = end.pre;
            end.next = null;
        } else if (node == head) {
            //移除头节点
            head = head.next;
            head.pre = null;
        } else {
            //移除中间节点
            node.pre.next = node.next;
            node.next.pre = node.pre;
        }
        return node.key;
    }

    /**
     * 尾部插入节点
     *
     * @param node 要插入的节点
     */
    private void addNode(Node node) {
        if (end != null) {
            end.next = node;
            node.pre = end;
            node.next = null;
        }
        end = node;
        if (head == null) {
            head = node;
        }
    }

    class Node {
        Node(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public Node pre;
        public Node next;
        public String key;
        public String value;
    }

    public static void main(String[] args) {

        LRUCache lruCache = new LRUCache(5);//预计全球url总数为4294967296,按每个请求0.2kb存储,需分配210G左右的内存
        lruCache.put("http://a.cn/1", "http://a.cn/kdjlakjikmkjkghalk_1");
        lruCache.put("http://a.cn/2", "http://a.cn/kdjlakjikmkjkghalk_1");
        lruCache.put("http://a.cn/3", "http://a.cn/kdjlakjikmkjkghalk_3");
        lruCache.put("http://a.cn/4", "http://a.cn/kdjlakjikmkjkghalk_4");
        lruCache.put("http://a.cn/5", "http://a.cn/kdjlakjikmkjkghalk_5");
        lruCache.get("http://a.cn/2");
        lruCache.put("http://a.cn/4", "http://a.cn/kdjlakjikmkjkghalk_4_更新");
        lruCache.put("http://a.cn/6", "http://a.cn/kdjlakjikmkjkghalk_6");
        System.out.println(lruCache.get("http://a.cn/1"));
        System.out.println(lruCache.get("http://a.cn/6"));
    }


}
