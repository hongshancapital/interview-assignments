package com.jinblog.shorturl.config;

import com.jinblog.shorturl.common.RecyclingStrategyEnum;
import com.jinblog.shorturl.common.GenerateModeEnum;
import com.jinblog.shorturl.common.UrlMapEnum;
import com.jinblog.shorturl.service.RecyclingStrategy;
import com.jinblog.shorturl.service.Generator;
import com.jinblog.shorturl.service.Storage;
import com.jinblog.shorturl.service.impl.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
@ConfigurationProperties("short-url")
public class ShortConfiguration implements InitializingBean {
    private static ShortConfiguration instance;
    private GenerateModeEnum generateMode;
    private UrlMapEnum urlMapMode;
    private RecyclingStrategyEnum recyclingStrategyMode;
    /*
    事件队列中最多能堆积的任务数量
     */
    private int maxPendingEvent = 1000;
    /**
     * 生产的短链接的长度
     */
    private int urlMaxLen = 8;
    /**
     * 开始垃圾回收的百分比
     */
    private double startRecyclingStrategyMemoryPercent = 0.7;
    /**
     * 开始停止服务，等待垃圾回收完毕的内存使用率百分比
     */
    private double highestMemoryPercent = 0.95;
    /**
     * 生成的短链接的domain前缀
     */
    private String shortUrlDomain;

    public void setGenerateMode(GenerateModeEnum generateMode) {
        this.generateMode = generateMode;
    }

    public GenerateModeEnum getGenerateMode() {
        return generateMode;
    }

    public void setRecyclingStrategyMode(RecyclingStrategyEnum recyclingStrategyMode) {
        this.recyclingStrategyMode = recyclingStrategyMode;
    }

    public RecyclingStrategyEnum getRecyclingStrategyMode() {
        return recyclingStrategyMode;
    }

    public void setUrlMapMode(UrlMapEnum urlMapMode) {
        this.urlMapMode = urlMapMode;
    }

    public UrlMapEnum getUrlMapMode() {
        return urlMapMode;
    }

    public int getUrlMaxLen() {
        return urlMaxLen;
    }

    public void setUrlMaxLen(int urlMaxLen) {
        this.urlMaxLen = urlMaxLen;
    }

    public void setMaxPendingEvent(int maxPendingEvent) {
        this.maxPendingEvent = maxPendingEvent;
    }

    public int getMaxPendingEvent() {
        return maxPendingEvent;
    }

    public void setHighestMemoryPercent(double highestMemoryPercent) {
        this.highestMemoryPercent = highestMemoryPercent;
    }

    public double getHighestMemoryPercent() {
        return highestMemoryPercent;
    }

    public void setStartRecyclingStrategyMemoryPercent(double startRecyclingStrategyMemoryPercent) {
        this.startRecyclingStrategyMemoryPercent = startRecyclingStrategyMemoryPercent;
    }

    public double getStartRecyclingStrategyMemoryPercent() {
        return startRecyclingStrategyMemoryPercent;
    }

    public void setShortUrlDomain(String shortUrlDomain) {
        this.shortUrlDomain = shortUrlDomain;
    }

    public String getShortUrlDomain() {
        return shortUrlDomain;
    }

    @Bean
    public Generator generator() {
        if (generateMode != null) {
            switch (generateMode) {
                case CHARACTER:
                    return new CharacterGenerator(getUrlMaxLen());
                case INTEGER:
                    return new IntegerGenerator(getUrlMaxLen());
            }
        }
        throw new RuntimeException("Generator error");
    }

    @Bean
    public Storage urlMap() {
        if (urlMapMode != null) {
            switch (urlMapMode) {
                case HASH:
                    return new StorageHash();
                case TRIE:
                    return new StorageTrie();
            }
        }
        throw new RuntimeException("StorageHash error");
    }

    @Bean
    public RecyclingStrategy recyclingStrategy() {
        if (recyclingStrategyMode != null) {
            switch (recyclingStrategyMode) {
                case LRU:
                    return new RecyclingStrategyLRUImpl();
                case NONE:
                    return new RecyclingStrategyNoneImpl();
            }
        }
        throw new RuntimeException("RecyclingStrategy error");
    }

    @Bean("shortUrlEventHandlerPool")
    public ExecutorService shortUrlEventHandlerPool() {
        return new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(maxPendingEvent), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (shortUrlDomain == null) {
            throw new RuntimeException("fill domain");
        }
        shortUrlDomain = shortUrlDomain.toLowerCase();
        if (!shortUrlDomain.startsWith("http://") && !shortUrlDomain.startsWith("https://")) {
            throw new RuntimeException("wrong domain format");
        }
        if (shortUrlDomain.charAt(shortUrlDomain.length() - 1) != '/') {
            shortUrlDomain = shortUrlDomain + '/';
        }
        instance = this;
    }

    public static ShortConfiguration getInstance() {
        return instance;
    }
}
