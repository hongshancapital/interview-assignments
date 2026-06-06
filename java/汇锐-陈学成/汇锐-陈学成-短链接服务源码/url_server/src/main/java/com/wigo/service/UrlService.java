package com.wigo.service;

import cn.hutool.core.util.RandomUtil;
import com.wigo.core.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wigo.chen
 * @date 2021/7/27 9:20 下午
 * Introduction: 长链接转短连接业务类
 */
@Service
@Slf4j
public class UrlService {

    //保存短链接和长链接关联映射, 保证并发时数据一致性使用线程安全的ConcurrentHashMap
    Map<String, String> URLS = new ConcurrentHashMap<>();
    //保存长链接和短链接关联映射,相同的长链接不用再次生成短链接, 保证并发时数据一致性使用线程安全的ConcurrentHashMap
    Map<String, String> LONG_URLS = new ConcurrentHashMap<>();
    //未防止内存泄漏，定期清理早期的链接映射, 保证并发时数据一致性使用线程安全的Vector
    List<String> URL_LIST = new Vector<>();
    //映射最大阈值，防止内存泄漏，尽量设置大一些
    public static int MAX = 1_000_000;
    //每次清理的数量
    public static int CLEAR_NUM = 10_000;
    //短链接域名前缀
    private String URL_PREFIX = "http://a.cn/";

    public String getShortUrl(String longUrl) {
        //如果已经生成了短链接，直接返回
        if(LONG_URLS.containsKey(longUrl)){
            return LONG_URLS.get(longUrl);
        }
        // 生成短连接
        String shortUrl = makeShortUrl(longUrl);
        return shortUrl;
    }

    public String getLongUrl(String shortUrl) throws CustomException {
        //检查映射中是否存在该短链接
        if (!URLS.containsKey(shortUrl)) {
            throw new CustomException("短链接不存在，请检查");
        }
        return URLS.get(shortUrl);
    }

    /**
     * 生成短链接, 使用最简单的随机数生成，此方法最大缺点，生成短链接
     *
     * @param longUrl 长链接
     * @return java.lang.String 短链接
     * @author wigo.chen
     * @date 2021/7/27 9:42 下午
     **/
    private String makeShortUrl(String longUrl) {
        /**
         * 传统做法可以mysql的自增索引或是可以考虑各种分布式key-value系统做发号器,目前比较流行的生成短码方法有：自增id、摘要算法、普通随机数.
         * 本次采用普通随机数，由于可能出现碰撞问题，所以采用循环生成并判断是否已存在与链接池中
         * */
        while (true) {
            String url = RandomUtil.randomString(8);
            String shortUrl = URL_PREFIX + url;
            if (!URLS.containsKey(shortUrl)) {
                //添加映射
                addMapping(shortUrl, longUrl);
                URLS.put(shortUrl, longUrl);
                return shortUrl;
            }
        }
    }

    /**
     * 记录映射关系
     *
     * @param shortUrl 短链接
     * @param longUrl  长链接
     * @return void
     * @author wigo.chen
     * @date 2021/7/27 11:49 下午
     **/
    private void addMapping(String shortUrl, String longUrl) {
        if (URL_LIST.size() > MAX) {
            //检查映射容量超过阈值，立刻执行清理
            checkThresholding();
        }
        URLS.put(shortUrl, longUrl);
        LONG_URLS.put(longUrl, shortUrl);
        URL_LIST.add(shortUrl);
    }

    /**
     * 映射数量超过阈值则清理超过的部分, 另一种思路，记录映射使用频率，优先清理低频使用的链接映射
     * 定时任务启动时间，应用启动后的5秒，频率每10秒
     *
     * @return void
     * @author wigo.chen
     * @date 2021/7/27 11:42 下午
     **/
    @Scheduled(initialDelay = 5000, fixedRate = 1000 * 10)
    private void checkThresholding() {
        //检查映射容量是否超过阈值
        if (URL_LIST.size() > MAX) {
            log.info("映射数量超过阈值，立刻启动清理");
            //删除超过阈值的映射，本次采用清理已设置的清理数量 MAX-CLEAR_NUM
            URL_LIST
                    .subList(MAX - CLEAR_NUM, URL_LIST.size())
                    .forEach(url -> {
                        URL_LIST.remove(url);
                        //先清理长链接
                        LONG_URLS.remove(URLS.get(url));
                        //再清理短链接
                        URLS.remove(url);
                    });
        }
    }
}
