package com.sequoiacap.shortlinkcenter.domain.shorturl.task;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.sequoiacap.shortlinkcenter.common.utils.ShortCodeUtils;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author xiuyuan
 * @date 2022/3/17
 */
@Slf4j
public class NotifierTask implements Runnable {

    private static BlockingQueue<String> tasks = new ArrayBlockingQueue<>(1024 * 1024);

    /**
     * 默认生成的短链大小（6为编码大概生成480亿左右，加上url失效日期，未来1-2年足够用，实在不够可以修改为8位）
     */
    private static final int SHORT_URL_BIT_DEFAULT = 6;

    /**
     * 短链重复过滤器
     */
    private static BloomFilter<String> shortUrlCodeBloomFilter = BloomFilter
            .create(Funnels.stringFunnel(StandardCharsets.UTF_8), 100000000, 0.0001);

    @Override
    public void run() {
        log.info("short NotifierTask started");
        for (; ;) {
            try {
                // 实际项目可以默认加载1w个，本地默认加载全部队列长度(1024 * 1024 = 1048576) 100W左右
                /*if (tasks.size() >= 10000) {
                    continue;
                }*/
                String shortUrlTempCode = ShortCodeUtils.generateShortUrl(SHORT_URL_BIT_DEFAULT);
                // 检查是否存在
                if (!shortUrlCodeBloomFilter.mightContain(shortUrlTempCode)) {
                    tasks.put(shortUrlTempCode);
                    shortUrlCodeBloomFilter.put(shortUrlTempCode);
                } else {
                    log.info("short NotifierTask exit: [{}] while handling notifying offer", shortUrlTempCode);
                }

            } catch (Throwable e) {
                log.error("short NotifierTask Error while handling notifying offer", e);
            }
        }
    }

    public static String getTask() {
        try {
            return tasks.poll();
        } catch (Throwable e) {
            log.error("short NotifierTask Error while handling notifying task", e);
        }
        return null;
    }

    public static BloomFilter<String> getShortUrlCodeBloomFilter() {
        return shortUrlCodeBloomFilter;
    }
}
