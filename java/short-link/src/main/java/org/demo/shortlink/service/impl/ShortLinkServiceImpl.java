package org.demo.shortlink.service.impl;

import com.github.yitter.idgen.YitIdHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.demo.shortlink.service.CacheService;
import org.demo.shortlink.service.ShortLinkService;
import org.demo.shortlink.utils.BloomUtil;
import org.demo.shortlink.utils.NumberUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wsq
 * @date 2022/3/26 002611:21
 * @description:
 */
@Service
@Slf4j
public class ShortLinkServiceImpl implements ShortLinkService {

    @Resource
    private CacheService cache;

    @Override
    public String generateShortLink(String longLink) {
        if (StringUtils.isBlank(longLink)) {
            throw new IllegalArgumentException("参数不能为空!");
        }

        try {
            URL url = new URL(longLink);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("参数无效!");
        }

        String shortLink = NumberUtil.encode10To62(YitIdHelper.nextId());
//        if(!BloomUtil.getInstance().contains(shortLink)) {
//            //TODO 不存在可插入数据库
//        }

        log.info("shortLink: {}",shortLink);
        cache.put(shortLink, longLink);
        //BloomUtil.getInstance().add(shortLink);
        return shortLink;
    }

    @Override
    public String findLongLink(String shortLink) {
        if (StringUtils.isBlank(shortLink)) {
            throw new IllegalArgumentException("参数不能为空!");
        }

        return cache.get(shortLink);
    }
}
