package com.zy.url.service.impl;

import com.zy.url.dao.UrlMapDao;
import com.zy.url.dto.UrlMapDto;
import com.zy.url.entity.UrlMap;
import com.zy.url.exception.BusinessException;
import com.zy.url.service.UrlMapService;
import com.zy.url.utils.CloneUtils;
import com.zy.url.utils.ShortUrlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
public class UrlMapServiceImpl implements UrlMapService {

    @Value("${url.domainName}")
    private String domainName;
    @Autowired
    private UrlMapDao urlMapDao;

    @Override
    public UrlMapDto createShortUrl(UrlMapDto urlMapDto) {
        if (Objects.isNull(urlMapDto) || StringUtils.isBlank(urlMapDto.getOriginUrl())) {
            throw new BusinessException("参数异常");
        }

        String[] shortUrls = ShortUrlUtils.getShortUrl(urlMapDto.getOriginUrl(), ShortUrlUtils.getShortUrlBySnowFlake());
        // 数组取随机下标短链接
        Random random = new Random();
        urlMapDto.setShortUrl(domainName + shortUrls[random.nextInt(shortUrls.length)]);
        UrlMap urlMap = CloneUtils.clone(urlMapDto, UrlMap.class);
        urlMapDao.save(urlMap);
        urlMapDto = CloneUtils.clone(urlMap, UrlMapDto.class);

        return urlMapDto;
    }

    @Override
    public UrlMapDto restoreUrl(UrlMapDto urlMapDto) {
        if (Objects.isNull(urlMapDto) || StringUtils.isBlank(urlMapDto.getShortUrl())) {
            throw new BusinessException("参数异常");
        }

        UrlMap urlMap = new UrlMap();
        urlMap.setShortUrl(urlMapDto.getShortUrl());
        Example<UrlMap> e = Example.of(urlMap);
        Optional<UrlMap> rst = urlMapDao.findOne(e);

        if (rst.get() != null) {
            urlMapDto = CloneUtils.clone(rst.get(), UrlMapDto.class);
        }

        return urlMapDto;
    }
}