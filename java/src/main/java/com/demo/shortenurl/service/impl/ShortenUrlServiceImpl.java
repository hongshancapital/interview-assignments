package com.demo.shortenurl.service.impl;

import com.demo.shortenurl.service.ShortenUrlService;
import com.demo.shortenurl.util.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 短域名服务实现
 * 本实现使用发号策略，从0自增，每来一个存储请求，就分配一个ID给对应的请求作为短URL
 * 采用62进制表示短URL ID，实际应用中短域名用不完，也不用做过期处理。本实现也默认这个前置条件
 * 由于是单机环境，本实现通过配置长URL最大长度和短URL最大数量避免内存溢出，同时对于超出最大数量的请求，返回一个默认短URL
 */
@Service
public class ShortenUrlServiceImpl implements ShortenUrlService {

    /**
     * 发号策略采用的id，使用AtomicLong保证唯一性
     */
    private AtomicLong shortUrlId = new AtomicLong(0);

    /**
     * 原始url的最大长度，在配置文件中通过shortenconfiguration.maxNumberOfShortenUrls配置
     */
    @Value(value = "${shortenconfiguration.maxLengthOfOriginalUrl}")
    private int maxLengthOfOriginalUrl;

    /**
     * 可存储的短URL集合最大数量，在配置文件中通过shortenconfiguration.maxNumberOfShortenUrls配置
     */
    @Value(value = "${shortenconfiguration.maxNumberOfShortenUrls}")
    private int maxNumberOfShortenUrls;

    /**
     * 长URL-》短URL映射集合，
     */
    private Map<String, String> mappingFromOriginalToShortenUrl = new HashMap<String, String>();

    /**
     * 短URL -> 长URL映射集合
     */
    private Map<String, String> mappingFromShortenToOrignalUrl = new HashMap<String, String>();

    /**
     * 读取接口实现，通过短URL查询到对应的原始URL。
     * @param shortenUrl 短URL
     * @return 如果短URL不合法，或者为“ZZZZZZZZ”，返回空值
     */
    @Override
    public String getOriginalUrl(String shortenUrl) {
        if (!Utils.isShortenUrlValid(shortenUrl)) {
            return Utils.EMPTY_STRING;
        }

        return this.queryOriginalUrlFromDatabase(shortenUrl);
    }

    /**
     * 短域名存储接口实现，对于一个原始的URL，生成一个短URL
     * @param originalUrl 原始URL
     * @return 如果短URL为空，或者长度超过配置的最大长度，返回空值。
     * 如果请求数量超过服务器所能存储的短URL最大数量，返回默认值“ZZZZZZZZ”
     */
    @Override
    public String getShortenUrl(String originalUrl) {
        if (Utils.isStringEmpty(originalUrl) || originalUrl.length() > this.maxLengthOfOriginalUrl) {
            return Utils.EMPTY_STRING;
        }

        String shortenUrl = this.queryShortenUrlFromDatabase(originalUrl);
        if (shortenUrl.isEmpty()) {
            if (this.shortUrlId.get() >= this.maxNumberOfShortenUrls) {
                shortenUrl = Utils.DEFAULT_ERROR_SHORTENURL;
            } else {
                long shortId = this.getShortenId();
                shortenUrl = Utils.encodeBy62Decimal(shortId);
                this.saveMapping(shortenUrl, originalUrl);
            }
        }

        return shortenUrl;
    }

    /**
     * maxLengthOfOriginalUrl 属性设置
     * @param maxLengthOfOriginalUrl 原始url的最大长度
     */
    public void setMaxLengthOfOriginalUrl(int maxLengthOfOriginalUrl) {
        this.maxLengthOfOriginalUrl = maxLengthOfOriginalUrl;
    }

    /**
     * maxNumberOfShortenUrls 属性设置
     * @param maxNumberOfShortenUrls 可存储的短URL集合最大数量
     */
    public void setMaxNumberOfShortenUrls(int maxNumberOfShortenUrls) {
        this.maxNumberOfShortenUrls = maxNumberOfShortenUrls;
    }

    /**
     * 在mapping存储中通过短URL查找原始URL
     * @param shortenUrl 短URL
     * @return 没有找到返回空值
     */
    private String queryOriginalUrlFromDatabase(String shortenUrl){
        String result = Utils.EMPTY_STRING;
        if (this.mappingFromShortenToOrignalUrl.containsKey(shortenUrl)) {
            result = this.mappingFromShortenToOrignalUrl.get(shortenUrl);
        }

        return result;
    }

    /**
     * 在mapping存储中通过原始URL查找短URL
     * @param originalUrl 原始URL
     * @return 没有找到返回空值
     */
    private String queryShortenUrlFromDatabase(String originalUrl){
        String result = Utils.EMPTY_STRING;
        if (this.mappingFromOriginalToShortenUrl.containsKey(originalUrl)) {
            result = this.mappingFromOriginalToShortenUrl.get(originalUrl);
        }

        return result;
    }

    /**
     * 将短URL和长URL互相的映射关系存储在map中
     * 本实现默认短URL不过期，采用发号策略，存储过程中不需要考虑并发一致性。
     * 后续实现如果改变，扩展需要考虑并发一致性
     * @param shortenUrl
     * @param originalUrl
     */
    private void saveMapping(String shortenUrl, String originalUrl) {
        this.mappingFromOriginalToShortenUrl.put(originalUrl, shortenUrl);
        this.mappingFromShortenToOrignalUrl.put(shortenUrl, originalUrl);
    }

    /**
     * 获得一个有效的短URL id
     * @return 短url id
     */
    private long getShortenId() {
        return this.shortUrlId.getAndIncrement();
    }
}
