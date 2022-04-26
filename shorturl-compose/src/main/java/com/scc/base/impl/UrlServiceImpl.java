package com.scc.base.impl;

import com.scc.base.entity.UrlDO;
import com.scc.base.mapper.UrlMapper;
import com.scc.base.service.Cache;
import com.scc.base.service.UrlService;
import com.scc.base.util.NumberTransformUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * @author renyunyi
 * @date 2022/4/24 2:51 PM
 * @description the implement of ShortUrlService
 **/
@Slf4j
@Service
public class UrlServiceImpl implements UrlService {

    @Resource
    private Cache<String, String> shortToLongCache;

    @Resource
    private Cache<String, String> longToShortCache;

    @Resource
    private UrlMapper urlMapper;

    @Override
    public String getShortUrlFrom(String longUrl) {
        //get from cache
        String shortUrl = longToShortCache.get(longUrl);

        //if cache is null
        if (StringUtils.isBlank(shortUrl)){
            UrlDO urlDO = new UrlDO();
            urlDO.setLongUrl(longUrl);

            //find in database
            Long id = urlMapper.getIdByLongUrl(urlDO);
            //id does not exist
            if (id == null){
                // insert long url to produce id
                long effectLines = urlMapper.addLongUrl(urlDO);
                if (effectLines == 1){
                    id = urlDO.getId();
                }

                if (id == null) {
                    log.error("insert into long url failed:{}", longUrl);
                    return null;
                }
            }
            shortUrl = NumberTransformUtils.decimalToBase62(id);

            //add to cache
            longToShortCache.put(longUrl, shortUrl);
            shortToLongCache.put(shortUrl, longUrl);
        }
        return shortUrl;
    }

    @Override
    public String getLongUrlFrom(String shortUrl) {
        //get from cache
        String longUrl = shortToLongCache.get(shortUrl);

        //if cache is null
        if (StringUtils.isBlank(longUrl)){
            //transform short url to id
            long id = NumberTransformUtils.base62ToDecimal(shortUrl);
            log.info("getLongUrlFrom id = {} ", id);

            UrlDO urlDO = new UrlDO();
            urlDO.setId(id);
            longUrl = urlMapper.getLongUrlById(urlDO);
            log.info("getLongUrlFrom longUrl = {}", longUrl);

            //add to cache
            longToShortCache.put(longUrl, shortUrl);
            shortToLongCache.put(shortUrl, longUrl);
        }

        return longUrl;
    }

}
