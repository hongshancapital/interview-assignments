package com.xwc.example.service.impl;

import com.xwc.example.commons.utils.CompressUtils;
import com.xwc.example.service.DomainService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 类描述：通过内存来实现的业务短域名服务的功能
 * 这种只适合单机服务
 * 作者：徐卫超 (cc)
 * 时间 2022/4/13 18:17
 */

@Component
public class MemoryDomainServiceImpl implements DomainService {


    // 计算器
    private final AtomicLong count = new AtomicLong();
    // 用于控制非线程安全的段锁
    private final int segment = Runtime.getRuntime().availableProcessors() * 2;

    // 存储短域名和长域名映射的集合
    private final Map<String, String> shortDomainMap = new ConcurrentHashMap<>();
    // 利用WeekHashMap用存储被使用的短域名和长域名映射的集合
    private final Map<String, Map<String, String>> useShortDomainMap = new ConcurrentHashMap<>();

    // 存储长域名和短域名映射的集合
    private final Map<String, String> longDomainMap = new ConcurrentHashMap<>();
    // 利用WeekHashMap用存储被使用的长域名和短域名映射的集合
    private final Map<String, Map<String, String>> useLongDomainMap = new WeakHashMap<>();

    @Value("${domain.shortHost:http://127.0.0.1:8080/}")
    private String shortHost;

    @Value("${domain.maxCapacity:10000000}")
    private Long maxCapacity;

    /**
     * 通过一个长域名返回一个短域名
     *
     * @param lengthDomain 长域名地址
     * @return 返回一个短域名信息
     */
    @Override
    public String getShortDomain(String lengthDomain) {
        if (shortDomainMap.size() > maxCapacity) {
            return null;
        }
        String shortPathVariable;
        // 当短域名不存在时才创建短域名
        if ((shortPathVariable = longDomainMap.get(lengthDomain)) == null) {
            // 从已经被用户使用的域名中查询是否被创建过
            int useLengthDomainSegment = Math.abs(lengthDomain.hashCode()) % segment;
            Map<String, String> useHashLengthDomainMap = useLongDomainMap.computeIfAbsent(useLengthDomainSegment + "",
                    val -> Collections.synchronizedMap(new WeakHashMap<>()));
            // 从已经使用过的路径中查询有没有没创建
            if ((shortPathVariable = useHashLengthDomainMap.get(lengthDomain)) == null) {
                // 系统没有该路径的短域名 就创建一个短域名
                shortPathVariable = CompressUtils.compressNumber(count.incrementAndGet());
                longDomainMap.put(lengthDomain, shortPathVariable);
                shortDomainMap.put(shortPathVariable, lengthDomain);
            }

        }
        return shortHost + shortPathVariable;
    }

    /**
     * 通过短域名查找一个长域名信息
     * <p>
     * 当一个域名被查找后 系统会自动把这个短域名的映射关系转移到 WeakHashMap中存储
     *
     * @param shortAddress 短域名地址
     * @return 长域名信息
     */
    @Override
    public String findLengthDomain(String shortAddress) {
        String shortDomain = shortAddress.substring(Math.min(shortAddress.length(), shortHost.length()));
        if (!StringUtils.hasText(shortDomain)) {
            return null;
        }
        // 被使用的域名 做一次转移
        String lengthDomain = shortDomainMap.remove(shortDomain);
        // 当短域名被使用过一次后把短域名放入到WeakHashMap减少系统OOM 也最大化的保证短域名能被重复使用
        int useShortDomainSegment = Math.abs(shortDomain.hashCode()) % segment;
        if (StringUtils.hasText(lengthDomain)) {
            longDomainMap.remove(lengthDomain);
            // 存储短域名
            Map<String, String> useShortDomainSegmentMap = useShortDomainMap.computeIfAbsent(useShortDomainSegment + "",
                    val -> Collections.synchronizedMap(new WeakHashMap<>()));
            useShortDomainSegmentMap.put(shortDomain, lengthDomain);

            // 存储长域名
            int useLengthDomainSegment = Math.abs(lengthDomain.hashCode()) % segment;
            Map<String, String> useLengthDomainSegmentMap = useLongDomainMap.computeIfAbsent(useLengthDomainSegment + "",
                    val -> Collections.synchronizedMap(new WeakHashMap<>()));
            useLengthDomainSegmentMap.put(lengthDomain, shortDomain);
            return lengthDomain;
        } else {
            Map<String, String> useShortDomainSegmentMap = useShortDomainMap.computeIfAbsent(useShortDomainSegment + "",
                    val -> Collections.synchronizedMap(new WeakHashMap<>()));
            return useShortDomainSegmentMap.get(shortDomain);
        }
    }

    /**
     * 销毁 lengthDomainMap  shortDomainMap 容器中太久没被使用的域名
     */
//    private void destroy() {
//        //TODO 后期实现
//
//    }
    @Override
    public void clear() {
        shortDomainMap.clear();
        useLongDomainMap.clear();
        count.set(0);
    }


}
