package com.diode.interview.infrastructure.repository.url;

import com.diode.interview.domain.ability.ConfigManager;
import com.diode.interview.domain.repository.MyURLRepository;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author unlikeha@163.com
 * @date 2022/4/28
 */
@Slf4j
@Repository
public class MyURLRepositoryImpl implements MyURLRepository {
    @Resource
    private ConfigManager configManager;

    private Cache<String, Object> cache;


    @PostConstruct
    public void initCache(){
        cache = Caffeine.newBuilder()
                .maximumSize(configManager.getCaffeineCacheMaxSize())  //最大长度
                .expireAfterWrite(configManager.getCaffeineCacheExpireSecs(), TimeUnit.SECONDS) //按时间淘汰
                .build();
    }

    /**
     * 两种映射都保存成功了才返回true
     * @param longURL 长链接
     * @param shortURL 短链接
     * @return 保存是否成功
     */
    @Override
    public boolean saveURL(String longURL, String shortURL, int expireSecs){
        boolean res1 = save(longURL, shortURL);
        if(!res1){
            return false;
        }
        //短链接-长链接应该用redis或者mysql做持久化更好，能够更大程度地保证做到指定时间短链接生效
        //expireSecs用于指定redis或mysql的生效时间，本地缓存解决方案中暂不使用
        return save(shortURL, longURL);
    }

    @Override
    public String getShortURL(String longURL){
        if (Strings.isNullOrEmpty(longURL)) {
            return null;
        }
        String res = (String) cache.getIfPresent(longURL);
        log.debug("longURL:{} shortURL:{}", longURL, res);
        return res;
    }

    /**
     * 根据短url获取长url，可以优化为从redis或者mysql中获取，此处从简依然从本地缓存中读
     * @param shortURL 短链接
     * @return 对应的长链接
     */
    @Override
    public String getLongURL(String shortURL){
        if (Strings.isNullOrEmpty(shortURL)) {
            return null;
        }
        String res = (String) cache.getIfPresent(shortURL);
        log.debug("shortURL:{} longURL:{}", shortURL, res);
        return res;
    }

    private boolean save(String key, Object value){
        try {
            if (Strings.isNullOrEmpty(key) || Objects.isNull(value)) {
                return true;
            }
            cache.put(key, value);
            return true;
        }catch (Exception e){
            log.error("url cache save error.key:{} value:{}", key, value, e);
            return false;
        }
    }
}
