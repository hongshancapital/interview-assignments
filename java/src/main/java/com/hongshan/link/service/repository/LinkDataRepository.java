package com.hongshan.link.service.repository;

import com.hongshan.link.service.constant.LinkConstant;
import com.hongshan.link.service.utils.Base62Utils;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author heshineng
 * created by 2022/8/8
 * <p>
 * link data 数据存储的位置
 * 大致思路（单机部署使用锁，并发度不高；集群使用redis自增或者数据库，并发度高）：
 * 1.设置2个Map，一个放长连接和自增数字的的映射；一个放数字和长连接的映射
 * 2.使用锁来操作2个map的存取，来保障线程安全
 * 3.长连接使用long 的数字，来实现映射
 */
@Repository
public class LinkDataRepository {

    /**
     * 自增id
     */
    private AtomicLong atomicLong = new AtomicLong(0);

    /**
     * 自增因子从1000开始，防止连续数字被人猜测
     */
    private final int INCREMENT_FACTOR = 1000;

    private ReentrantLock lock = new ReentrantLock();

    /**
     * 长链接映射id的map
     */
    private Map<String, String> linkMap = new HashMap<>();

    /**
     * id映射长链接的map
     */
    private Map<String, String> idMap = new HashMap<>();

    /**
     * 生成唯一自增id
     *
     * @return
     */
    private long generateUniqueId() {
        return atomicLong.addAndGet(INCREMENT_FACTOR);
    }

    /**
     * 长连接映射map中是否包含长连接
     *
     * @param longLink
     * @return
     */
    public boolean isContainLinkByLinkMap(String longLink) {
        try {
            lock.lock();
            return linkMap.containsKey(longLink);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 从map中获取短连接
     *
     * @param longLink
     * @return
     */
    public String getShortLink(String longLink) {
        try {
            lock.lock();
            String id = linkMap.get(longLink);
            return LinkConstant.SHORT_LINK_HOST + id;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 创建新的短连接
     *
     * @param longLink
     * @return
     */
    public String createShortLink(String longLink) {
        try {
            lock.lock();
            long num = generateUniqueId();
            String base62Str = Base62Utils.getBase62Str(num);
            linkMap.put(longLink, base62Str);
            idMap.put(base62Str, longLink);
            return LinkConstant.SHORT_LINK_HOST + base62Str;
        } finally {
            lock.unlock();
        }
    }

    /**
     * id map映射中是否包含这个短链接id
     *
     * @param id
     * @return
     */
    public boolean isContainIdByIdMap(String id) {
        try {
            lock.lock();
            return idMap.containsKey(id);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 通过id获取长连接数据
     *
     * @param id
     * @return
     */
    public String getLongLink(String id) {
        try {
            lock.lock();
            return idMap.get(id);
        } finally {
            lock.unlock();
        }
    }
}
