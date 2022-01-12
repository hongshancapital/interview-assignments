package com.example.demo.model.utils;


import cn.hutool.core.util.StrUtil;
import com.example.demo.config.UrlConfig;
import com.example.demo.model.vo.UrlNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author wangxiaosong
 * @since 2022/1/10
 */
@Component
public class NodeUtil {

    // key: shortUrl value: UrlNode(longUrl,ttl)
    private static final Map<String, UrlNode<String>> nodeMap = new ConcurrentHashMap<>();

    // 定时处理过期短url
    private static final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    @Autowired
    private UrlConfig urlConfig;

    // 多长时间清理缓存
    private volatile long  range;

    @PostConstruct
    public void post() {
        // 通过线程池一天清理一次缓存
        scheduledExecutorService.scheduleAtFixedRate(new CacheCleanTask(), 0, urlConfig.getPeriod(), urlConfig.getTimeUnit());
    }

    public String get(String shortUrl) {
        UrlNode<String> node = nodeMap.get(shortUrl);
        if (node == null){
            return StrUtil.EMPTY;
        }
        //重新刷新node过期时间，热点数据永久保存
        node.setExpireTime(System.currentTimeMillis());
        return node.getKey();
    }

    public String put(String key, String value) {
        UrlNode<String> expireNode = nodeMap.put(key, new UrlNode<>(value, System.currentTimeMillis()));
        // expireNode为空则返回空串
        return expireNode == null ? StrUtil.EMPTY : expireNode.getKey();
    }

    /**
     * 缓存清理任务
     */
    static class CacheCleanTask implements Runnable {
        @Override
        public void run() {
            Date date=new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            //不常用10天没用的缓存会被清理掉
            calendar.add(Calendar.DAY_OF_MONTH, -10);
            long time = calendar.getTime().getTime();
            if (nodeMap.size() != 0) {
                nodeMap.keySet().removeIf(key -> nodeMap.get(key).getExpireTime() <= time);
            }
        }
    }

}
