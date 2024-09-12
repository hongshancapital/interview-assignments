package com.oldnoop.shortlink.storage;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.lucene.util.RamUsageEstimator;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class MemoryLinkStorage implements LinkStorage {

    private BiMap<String, String> linkMap = HashBiMap.create();

    private ScheduledExecutorService exector;

    private long size = 1 << 25;

    private long checkInterval = 10;

    private volatile boolean exceed = false;

    public MemoryLinkStorage() {
        this(0);
    }

    public MemoryLinkStorage(long size) {
        if (size > 0) {
            this.size = size;
        }
        linkMap.inverse();
    }

    @PostConstruct
    public void init() {
        exector = Executors.newSingleThreadScheduledExecutor();
        exector.scheduleWithFixedDelay(() -> {
            calculateSize();
        }, checkInterval, checkInterval, TimeUnit.SECONDS);
    }

    @Override
    public void save(String shortLink, String longLink) {
        if (exceed) {
            throw new RuntimeException("短链接超出存储限制");
        }
        linkMap.put(shortLink, longLink);
    }

    @Override
    public String getLongLink(String shortLink) {
        return linkMap.get(shortLink);
    }

    @Override
    public String getShortLink(String longLink) {
        return linkMap.inverse().get(longLink);
    }

    @Override
    public void remove(String shortLink) {
        linkMap.remove(shortLink);
    }

    private void calculateSize() {
        boolean actualExceed = RamUsageEstimator.sizeOf(linkMap) >= size;
        if (exceed != actualExceed) {
            exceed = actualExceed;
        }
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setCheckInterval(long checkInterval) {
        this.checkInterval = checkInterval;
    }

}
