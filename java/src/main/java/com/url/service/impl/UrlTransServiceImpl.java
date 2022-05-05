package com.url.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.google.common.hash.BloomFilter;
import com.url.bean.UrlResultBean;
import com.url.error.BusinessCode;
import com.url.service.UrlTransService;
import com.url.utils.DomainCheckUtil;
import com.url.utils.Long62Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 短域名获取服务类
 * @Author jeckzeng
 * @Date 2022/4/30
 * @Version 1.0
 */
@Service
@Slf4j
public class UrlTransServiceImpl implements UrlTransService {

    @Value("${url.domain}")
    private String shortDomain;

    @Resource
    private BloomFilter<String> bloomFilter;

    @Resource
    private Cache<String,String> urlCache;

    private AtomicLong seqNumber = new AtomicLong();

    /**
     * 根据long url 获取 short url
     * 1.判断传递的url是否为空 或 是否是一个正确的url
     * 2.通过原子类自增生成一个数字，保证线程安全
     * 3.把生成好的数字转62进制
     * 4.加入内存缓存 和 布隆过滤器
     * 5.return
     * @param longUrl
     * @return
     */
    @Override
    public UrlResultBean getShortUrl(String longUrl) {
        //检查必要参数 参数为空或者不是一个正确的url
        try {
            if(StringUtils.isEmpty(longUrl) || !DomainCheckUtil.check(longUrl)){
                return new UrlResultBean(BusinessCode.PARAMETER_EMPTY,"");
            }
            Long seq = seqNumber.getAndIncrement();
            String shortUrl = Long62Util.to62String(seq);
            urlCache.put(shortUrl,longUrl);
            bloomFilter.put(shortUrl);
            shortUrl = shortDomain + shortUrl;
            return new UrlResultBean(BusinessCode.SUCCESS,shortUrl);
        } catch (Exception e){
            log.error("获取url服务异常,error:{}",e.getMessage());
            return new UrlResultBean(BusinessCode.SERVER_ERROR,"");
        }
    }

    /**
     * 根据short url 获取 long url
     * 1.判断参数是否为空
     * 2.截取short url后缀标识
     * 3.判断布隆过滤器是否有，如果有从缓存取出long url
     * 4.如果缓存取出来的值不为空，则返回long url；如果为空则返回异常（可能命中了布隆过滤器的误差率或缓存已经过期了）
     * @param shortUrl
     * @return
     */
    @Override
    public UrlResultBean getLongUrl(String shortUrl) {
        try {
            if(StringUtils.isEmpty(shortUrl)){
                return new UrlResultBean(BusinessCode.PARAMETER_EMPTY,"");
            }
            //从布隆过滤器中判断是否存在，不存在直接返回，存在则从缓存中获取
            shortUrl = shortUrl.substring(shortUrl.lastIndexOf("/") + 1);
            if(bloomFilter.mightContain(shortUrl)){
                String longUrl = urlCache.getIfPresent(shortUrl);
                if(!StringUtils.isEmpty(longUrl)){
                    return new UrlResultBean(BusinessCode.SUCCESS,longUrl);
                }
            }
        } catch (Exception e){
            log.error("获取长域名    服务异常,error:{}",e.getMessage());
            return new UrlResultBean(BusinessCode.SERVER_ERROR,"");
        }
        return new UrlResultBean(BusinessCode.EXPIRE_OR_NOT_EXISTS,"");
    }
}
