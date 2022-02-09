package com.homework.shorturl.service;

import com.homework.shorturl.exception.ExceptionCode;
import com.homework.shorturl.exception.ShortUrlBusinessException;
import com.homework.shorturl.res.FullUrlRes;
import com.homework.shorturl.res.ShortUrlRes;
import com.homework.shorturl.utils.ShortUrlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class ShortUrlService {
    private HashMap<Long, String> shortUrlMap = new HashMap<>();

    // 短链接最大长度8的极限数量，通过62位自定义字符最后一位组合"66666666"反推计算
    private static final long MAX_ID_NUMBER = 218340105584895L;
    // 根据业务场景&机器RAM性能定义阈值档位
    private static final String FREE_MEMORY_MAX_PERCENT = "0.20";

    private long idFlag = 1L;

    @Value("${shorturl.prefix}")
    private String shortUrlPrefix;

    public ShortUrlRes generateShortUrlByFullUrl(String fullUrl) {
        memoryWatchDog();
        validateInput(fullUrl);

        // 检查是否存在，存在则返回原来生成的
        if (shortUrlMap.containsValue(fullUrl)) {
            long existId = getKeysByStream(fullUrl);
            return new ShortUrlRes(getShortUrl(existId));
        } else {
            shortUrlMap.put(idFlag, fullUrl);
            String shortUrl = getShortUrl(idFlag);
            idFlag ++;
            return new ShortUrlRes(shortUrl);
        }
    }

    public FullUrlRes getFullUrlByShortUrl(String ShortUrl) {
        if (ShortUrl.startsWith(shortUrlPrefix)) {
            String url = ShortUrl.replaceFirst(shortUrlPrefix, "");
            String result = shortUrlMap.get(ShortUrlUtil.stringToId(url));
            if (StringUtils.hasText(result)) {
                return new FullUrlRes(result);
            } else {
                throw new ShortUrlBusinessException(ExceptionCode.NOT_FOUND, String.format("Short url: %s not found full url", ShortUrl));
            }
        } else {
            throw new ShortUrlBusinessException(ExceptionCode.URL_NOT_VALID, String.format("Short url: %s not valid", ShortUrl));
        }
    }

    private String getShortUrl(long existId) {
        return shortUrlPrefix + ShortUrlUtil.idToString(existId);
    }

    private <V> Long getKeysByStream(V value) {
        return shortUrlMap.entrySet()
                .stream()
                .filter(kvEntry -> Objects.equals(kvEntry.getValue(), value))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new ShortUrlBusinessException("Current url not found in records, this will not happen :) just make code stronger"));
    }

    private void validateInput(String fullUrl) {
        if (!ShortUrlUtil.isValidUrl(fullUrl)) {
            throw new ShortUrlBusinessException(ExceptionCode.URL_NOT_VALID, "Input url not valid");
        }
        if (idFlag > MAX_ID_NUMBER) {
            throw new ShortUrlBusinessException(ExceptionCode.EXCEED_MAX_NUMBER, "Out of max number of short url number");
        }
    }

    private void memoryWatchDog() {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        BigDecimal totalMemory = new BigDecimal(runtime.totalMemory()).setScale(2, RoundingMode.HALF_UP);
        BigDecimal freeMemory = new BigDecimal(runtime.freeMemory()).setScale(2, RoundingMode.HALF_UP);
        BigDecimal freeMemoryPercent = freeMemory.divide(totalMemory, 2, RoundingMode.HALF_UP);
        log.info("Current jvm free memory is: {}, and percentage of total memory is: {}", runtime.freeMemory(), freeMemoryPercent);
        if (freeMemoryPercent.compareTo(new BigDecimal(FREE_MEMORY_MAX_PERCENT)) < 0) {
            //超过了阈值比例，为了防止out of memory,这里个人想了三种方案：
            //1.将不再接收数据存储在jvm内存中。
            //2.clear HashMap
            //3.将每个进入到hashmap的值给定一个时间戳，清除时间过长的数据，但是还是可能会溢出，所以还是得进行1，2
            shortUrlMap = new HashMap<>();
            clearAll();
            //这些处理方式都是在非常不make sense的前提条件（将数据存在jvm内存中）下进行试想的，希望有大神能给出更好的解决方案学习。
        }
    }

    protected void clearAll() {
        idFlag = 1L;
        log.info("clear hashmap already");
    }
}
