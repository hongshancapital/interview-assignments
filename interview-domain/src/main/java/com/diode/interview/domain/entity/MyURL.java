package com.diode.interview.domain.entity;

import com.diode.interview.domain.exception.BizException;
import com.diode.interview.domain.ability.Transformer;
import com.diode.interview.domain.repository.MyURLRepository;
import com.google.common.base.Strings;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author unlikeha@163.com
 * @date 2022/4/28
 */
@Slf4j
@Scope("prototype")
@Component
public class MyURL {

    private static final String LONG_URL_PATTERN = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    private static final String SHORT_URL_PATTERN = "^.{0,8}$";

    @Setter
    @Getter
    private String url;
    @Setter
    @Getter
    private int expireSecs = 3600;

    @Resource
    private MyURLRepository myURLRepository;

    public String longToShort(Transformer transformer) {
        //空校检
        if (Strings.isNullOrEmpty(url)) {
            log.warn("url is blank! transformer:{}", transformer);
            return null;
        }
        //空校检
        if (Objects.isNull(transformer)) {
            log.warn("transformer is blank! url:{}", url);
            return null;
        }
        //格式校检
        if (isShortURL(url)) {
            log.warn("longToShort url is not long url! url:{}", url);
            throw new BizException("链接必须为长链接！");
        }
        //先查找是否存在本地缓存
        String existedURL = null;
        try {
            existedURL = myURLRepository.getShortURL(this.url);
        } catch (Exception e) {
            //此处考虑到缓存的可用性，选择放行，重新计算一遍
            //生产环境中可以通过一个降级开关来决定是否返回
            log.error("get url error! url:{}", url);
        }
        //如果已存在就直接返回
        if (!Strings.isNullOrEmpty(existedURL)) {
            log.debug("url cached. long:{}, short:{}", url, existedURL);
            return existedURL;
        }
        String transformedURL = transformer.transform(this.url);
        //保存链接关系
        boolean res = myURLRepository.saveURL(url, transformedURL, expireSecs);
        if (!res) {
            throw new BizException("链接保存失败！");
        }
        return transformedURL;
    }

    public String shortToLong() {
        //空校检
        if (Strings.isNullOrEmpty(url)) {
            log.warn("url is blank!");
            return null;
        }
        //格式校检
        if (isLongURL(url)) {
            log.warn("shortToLong url is not short url! url:{}", url);
            throw new BizException("链接必须为短链接！");
        }
        String existedURL;
        try {
            existedURL = myURLRepository.getLongURL(this.url);
        } catch (Exception e) {
            log.error("get url error! url:{}", url);
            throw new BizException("获取长链接失败！", e);
        }
        return existedURL;
    }

    public static boolean isShortURL(String url) {
        return Pattern.matches(SHORT_URL_PATTERN, url);
    }

    public static boolean isLongURL(String url) {
        return Pattern.matches(LONG_URL_PATTERN, url);
    }
}
