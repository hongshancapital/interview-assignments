package com.sequoiacap.shortlinkcenter.domain.shorturl.entity;

import com.google.common.hash.BloomFilter;
import com.sequoiacap.shortlinkcenter.common.domain.AggregateRoot;
import com.sequoiacap.shortlinkcenter.common.utils.ShortCodeUtils;
import com.sequoiacap.shortlinkcenter.domain.exception.DomainErrorCodeEnum;
import com.sequoiacap.shortlinkcenter.domain.exception.DomainException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author h.cn
 * @date 2022/3/17
 */
@Data
@Slf4j
public class UrlEntity implements AggregateRoot<String> {

    private static Logger logger = LoggerFactory.getLogger(UrlEntity.class);

    private static final long serialVersionUID = 2139670782351438844L;

    /**
     * 源url
     */
    private String sourceUrl;

    /**
     * url协议
     */
    private String protocol;

    /**
     * 目标url（短链）
     */
    private String targetUrl;

    /**
     * url code
     */
    private String urlCode;

    @Override
    public String getId() {
        return urlCode;
    }

    /**
     * 源url校验
     */
    public void sourceUrlValidate() {
        if (StringUtils.isBlank(sourceUrl)) {
            throw new DomainException(DomainErrorCodeEnum.VALIDATE_ERROR.getCode(), "url为空");
        }
    }

    /**
     * 解析协议
     */
    public void parseProtocol() {
        try {
            URL url = new URL(sourceUrl);
            this.protocol = url.getProtocol();
        } catch (MalformedURLException e) {
           logger.error("UrlEntity sourceUrl: [{}] parseProtocol is error: ", sourceUrl, e);
           throw new DomainException(DomainErrorCodeEnum.PROTOCOL_NOT_EXIST.getCode(), "协议无效");
        }
    }

    /**
     * 构建目标短链
     * @param targetDomainName 目标域名
     */
    public void buildTargetUrl(String targetDomainName) {
        this.targetUrl = this.protocol + "://" + targetDomainName + "/" + this.urlCode;
    }

    /**
     * 生成短链编码
     * @param num 短链位数
     * @param shortUrlCodeBloomFilter 短链过滤器
     */
    public void buildShortUrlCode(int num, BloomFilter<String> shortUrlCodeBloomFilter) {
        // 如果urlCode存在，不再构建（预生成）
        if (StringUtils.isNotBlank(urlCode)) {
            return;
        }
        // 自己生成短链编码
        // （队列为空，说明此时消费速度>生产速度 活着生产异常，可以降级+限流处理，由于本地实现所以自己生成）
        // 打印warn日志，方便监控
        log.warn("ShortUrlServiceImpl getShortUrlBySourceUrl sourceUrl: [{}] local queue is empty or error", sourceUrl);
        // 第一次生成
        String shortUrlTempCode = ShortCodeUtils.generateShortUrl(num);
        // 检查是否存在,否则循环生成5次
        if (StringUtils.isNotBlank(shortUrlTempCode) && !shortUrlCodeBloomFilter.mightContain(shortUrlTempCode)) {
            this.urlCode = shortUrlTempCode;
            shortUrlCodeBloomFilter.put(shortUrlTempCode);
            return;
        }
        int count = 5;
        while (count>0) {
            shortUrlTempCode = ShortCodeUtils.generateShortUrl(num);
            if (StringUtils.isNotBlank(shortUrlTempCode) && !shortUrlCodeBloomFilter.mightContain(shortUrlTempCode)) {
                this.urlCode = shortUrlTempCode;
                shortUrlCodeBloomFilter.put(shortUrlTempCode);
                break;
            }
            count--;
        }
        if (StringUtils.isBlank(this.urlCode)) {
            logger.error("UrlEntity sourceUrl: [{}] buildShortUrlCode retry 5 count failed: ", sourceUrl);
            throw new DomainException(DomainErrorCodeEnum.RETRY_FAILED.getCode(), "url生成失败，请稍后再试");
        }
    }
}
