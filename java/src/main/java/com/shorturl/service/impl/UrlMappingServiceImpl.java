package com.shorturl.service.impl;

import com.shorturl.utils.NumberUtils;
import com.shorturl.cache.Cache;
import com.shorturl.dao.UrlMappingDao;
import com.shorturl.service.ServerVersionService;
import com.shorturl.service.urlMappingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class UrlMappingServiceImpl implements urlMappingService {

    private static final Logger logger = LoggerFactory.getLogger(UrlMappingServiceImpl.class);

    @Autowired
    private ServerVersionService serverVersionService;

    @Autowired
    private UrlMappingDao urlMappingDao;

    @Autowired
    private Cache cache;

    @Value("${site:titizz.com/}")
    private String site;

    private volatile Long version;

    @PostConstruct
    public void init() throws Exception {

        version = serverVersionService.updateVersion();
        logger.info("initialize, get initial code {}", version);
    }

    public String urlCompress(String url) throws Exception {

        Long code = cache.getCode(url);
        if (code != null) {
            logger.info("hit cache, code {} correspond with url {}", code, url);
            return assembleShortUrl(code);
        }

        code = urlMappingDao.insert(version, url);
        logger.info("no available code, prepare to update initial code");
        urlMappingDao.delete(code);
        synchronized (this) {
            serverVersionService.updateVersion();
            logger.info("get new initial code {}", version);
        }

        cache.put(code.toString(), url);

        return assembleShortUrl(code);
    }

    public String urlDecompress(String base62) throws Exception {

        Long code = NumberUtils.base62ToDecimal(base62);
        String url = cache.getUrl(code.toString());
        if (url != null) {
            logger.info("hit cache, url {} correspond with code {}, base62 {}", url, code, base62);
            return url;
        }

        try {
            url = urlMappingDao.queryUrl(code);
        } catch (EmptyResultDataAccessException e) {
            logger.info("no query result, code {}", code);
            throw new Exception();
        }

        cache.put(code.toString(), url);
        return url;
    }

    private String assembleShortUrl(Long code) {
        return assembleShortUrl(NumberUtils.decimal2base62(code));
    }

    private String assembleShortUrl(String base62) {
        return site.endsWith("/") ? site + base62 : site + "/" + base62;
    }
}
