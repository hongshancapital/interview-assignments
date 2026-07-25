package org.faof.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = {"classpath:application.yml"})
public class ApplicationProperty {

    @Value("${application.cache.initCapacity}")
    private int cacheInitCapacity;

    @Value("${application.cache.maxSize}")
    private int cacheMaxSize;

    @Value("${application.prefix}")
    private String shortUrlPrefix;

    @Value("${application.arraySize}")
    private int arraySize;

    @Value("${application.maxCapacity}")
    private long maxCapacity;

    public int getCacheInitCapacity() {
        return cacheInitCapacity;
    }

    public int getCacheMaxSize() {
        return cacheMaxSize;
    }

    public String getShortUrlPrefix() {
        return shortUrlPrefix;
    }

    public int getArraySize() {
        return arraySize;
    }

    public long getMaxCapacity() {
        return maxCapacity;
    }
}
