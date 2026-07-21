package com.scdt.urlshorter.utils.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 缓存分段类
 *
 * @author mingo
 */
public class CacheSegment<K, V>
{
    /**
     * 缓存最大容量
     */
    private final int maxCapacity;

    /**
     * 节点MAP
     */
    private final Map<K, Node<K, V>> nodeMap;

    /**
     * 链表的头、尾节点
     */
    private Node<K, V> head, tail;

    /**
     * 定义可重入读写锁
     */
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * 读锁
     */
    private final Lock readLock = readWriteLock.readLock();

    /**
     * 写锁
     */
    private final Lock writeLock = readWriteLock.writeLock();

    /**
     * Node节点类
     *
     * @param <K> key
     * @param <V> value
     */
    private static class Node<K, V>
    {
        /**
         * 缓存KEY
         */
        private final K key;

        /**
         * 缓存value
         */
        private V value;

        /**
         * 前驱、后驱节点
         */
        private Node<K, V> next, previous;

        /**
         * 构造函数
         *
         * @param key   key
         * @param value value
         */
        public Node(K key, V value)
        {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString()
        {
            return "Node{" +
                    "key=" + key +
                    ", value=" + value +
                    ", next=" + next +
                    ", previous=" + previous +
                    '}';
        }
    }

    /**
     * 构造函数
     *
     * @param maxCapacity 最大容量
     */
    public CacheSegment(int maxCapacity)
    {
        this.maxCapacity = maxCapacity;
        nodeMap = new HashMap<>();
    }

    /**
     * 存放数据
     *
     * @param key   key
     * @param value value
     */
    public void put(K key, V value)
    {
        //写锁
        writeLock.lock();
        try
        {
            if (nodeMap.containsKey(key))
            {
                Node<K, V> node = nodeMap.get(key);
                node.value = value;
                //移除节点
                remove(node);
                //重新放置节点到链表尾部
                offer(node);
            }
            else
            {
                //超过最大容量则删除头部节点
                if (nodeMap.size() >= maxCapacity)
                {
                    nodeMap.remove(head.key);
                    remove(head);
                }
                //构建节点，将节点放置于链表尾部
                Node<K, V> node = new Node<>(key, value);
                offer(node);
                nodeMap.put(key, node);
            }
        }
        finally
        {
            //释放锁资源
            writeLock.unlock();
        }
    }

    /**
     * 通过KEY取得节点（每次访问后，都要将该节点元素移动到链表尾部）
     *
     * @param key key
     * @return 返回节点值
     */
    public V get(K key)
    {
        //写锁
        writeLock.lock();
        try
        {
            Node<K, V> node = nodeMap.get(key);
            if (node == null)
            {
                return null;
            }
            //删除节点
            remove(node);
            //重新放置到链表尾部
            offer(node);
            return node.value;
        }
        finally
        {
            //释放锁资源
            writeLock.unlock();
        }
    }

    /**
     * 取得分段MAP的大小
     *
     * @return map的大小
     */
    public int size()
    {
        //读锁
        readLock.lock();
        try
        {
            //分段MAP的大小
            return nodeMap.size();
        }
        finally
        {
            //释放锁资源
            readLock.unlock();
        }
    }

    /**
     * 调整节点位置
     *
     * @param node 节点
     */
    private void offer(Node<K, V> node)
    {
        if (node == null)
        {
            return;
        }

        if (head == null)
        {
            head = tail = node;
        }
        else
        {
            //将节点放到链表的尾部
            tail.next = node;
            node.previous = tail;
            node.next = null;
            tail = node;
        }
    }

    /**
     * 移除NODE节点
     *
     * @param node 节点
     */
    private void remove(Node<K, V> node)
    {
        if (node == null)
        {
            return;
        }

        if (node.previous != null)
        {
            //修改后驱节点指向
            node.previous.next = node.next;
        }
        else
        {
            //修改头节点指向
            head = node.next;
        }

        if (node.next != null)
        {
            //修改前驱节点指向
            node.next.previous = node.previous;
        }
        else
        {
            //修改尾节点指向
            tail = node.previous;
        }
    }
}
