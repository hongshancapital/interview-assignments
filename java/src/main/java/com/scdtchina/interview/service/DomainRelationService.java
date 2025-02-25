package com.scdtchina.interview.service;

import com.scdtchina.interview.dto.MyLinkedHashMap;
import com.scdtchina.interview.enums.ErrorCodeEnum;
import com.scdtchina.interview.exception.BusinessException;
import com.scdtchina.interview.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

import static com.scdtchina.interview.constants.CommonConstant.CACHE_SIZE;

@Service
public class DomainRelationService implements InitializingBean {
    private Map<String, String> myLinkedHashMap;

    /**
     * 长域名生成短域名
     * @param longUrl 长域名地址
     * @return 短域名地址
     */
    public String shortenUrl(String longUrl) {
        if (StringUtils.isBlank(longUrl)) {
            return null;
        }
        longUrl = longUrl.trim();
        String shortUrl = CommonUtil.getShortUrl(longUrl);
        if (!myLinkedHashMap.containsKey(shortUrl)) {
            myLinkedHashMap.put(shortUrl, longUrl);
        }
        return shortUrl;
    }

    /**
     * 根据短域名返回长域名
     * @param shortUrl 短域名地址
     * @return 长域名地址
     */
    public String getLongUrl(String shortUrl) {
        if (StringUtils.isBlank(shortUrl)) {
            return null;
        }
        shortUrl = shortUrl.trim();
        if (!myLinkedHashMap.containsKey(shortUrl)) {
            throw new BusinessException(ErrorCodeEnum.SHORT_URL_INVALID);
        }
        return myLinkedHashMap.get(shortUrl);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        myLinkedHashMap = Collections.synchronizedMap(new MyLinkedHashMap(CACHE_SIZE));
    }
}
