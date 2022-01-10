package com.liuxiang.biz;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.liuxiang.dao.ShortUrlMappingDao;
import com.liuxiang.model.po.ShortUrlMappingPo;
import com.liuxiang.model.view.CommonResult;
import com.liuxiang.service.IGenerateId;
import com.liuxiang.service.IShortIdExist;
import com.liuxiang.util.BloomSingleton;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.Resource;

/**
 * @author liuxiang6
 * @date 2022-01-06
 **/
@Service
@Slf4j
public class ShortUrlBiz {

    /**
     * 本地缓存最大个数
     */
    private static final long MAX_CACHE_NUM = 1000000L;

    /**
     * 缓存最长时间
     */
    private static final int MAX_CACHE_TIME_SECOND = 10;

    /**
     * 本地缓存
     */
    public static final Cache<String, String> LOCAL_CACHE = CacheBuilder.newBuilder().maximumSize(MAX_CACHE_NUM)
        .expireAfterAccess(MAX_CACHE_TIME_SECOND, TimeUnit.SECONDS).build();

    public Lock lock = new ReentrantLock();

    /**
     * 生成短链算法顺序
     */
    @Value("${generate.algorithm}")
    private String ALGORITHMS;

    @Value("${local.host}")
    private String LOCAL_HOST;

    @Autowired
    private List<IGenerateId> generateIdList;
    @Autowired
    private IShortIdExist stringExist;
    @Resource
    private ShortUrlMappingDao shortUrlMappingDao;

    public CommonResult<String> generateShortUrl(String longUrl) {

        //按照配置的算法顺序生成短链
        List<String> algorithmList = Arrays.asList(ALGORITHMS.split(","));
        for (String hashAlgorithm : algorithmList) {
            String shortUrl = generate(hashAlgorithm, longUrl.trim());
//            log.info("getShortUrl hashAlgorithm:{},longUrl:{},shortUrl:{}", hashAlgorithm, longUrl, shortUrl);
            if (StringUtils.isNotBlank(shortUrl)) {
                return CommonResult.success(shortUrl);
            }
        }

        return CommonResult.defaultError();
    }

    private String generate(String hashAlgorithm, String longUrl) {
        IGenerateId generateId = generateIdList.stream().filter(s -> s.generateType().equals(hashAlgorithm)).findAny().orElse(null);
        if (Objects.isNull(generateId)) {
            log.info("generate is null. hashAlgorithm:{},longUrl:{}", hashAlgorithm, longUrl);
            return null;
        }

        String shortId = generateId.generate(longUrl);
        lock.lock();
        try {
            //用布隆过滤器查看是否已经存在
            boolean exist = stringExist.isExist(shortId);
            if (exist) {
                String ifPresent = LOCAL_CACHE.getIfPresent(shortId);
                if (!longUrl.equals(ifPresent)) {
                    log.info("generate exist. hashAlgorithm:{},longUrl:{},short:{}", hashAlgorithm, longUrl, shortId);
                    return null;
                } else {
                    return LOCAL_HOST + shortId;
                }
            }

            ShortUrlMappingPo shortUrlMappingPo = new ShortUrlMappingPo();
            shortUrlMappingPo.setShortUrl(shortId);
            shortUrlMappingPo.setLongUrl(longUrl);
            shortUrlMappingPo.setCreateTime(System.currentTimeMillis());
            shortUrlMappingDao.insert(shortUrlMappingPo);

            //更新本地缓存和布隆过滤器
            LOCAL_CACHE.put(shortId, longUrl);
            BloomSingleton.getInstance().put(shortId);
        }finally {
            lock.unlock();
        }

        return LOCAL_HOST + shortId;
    }

    public CommonResult<String> getLongUrl(String shortUrl) {
        String shortId = shortUrl.replaceFirst(LOCAL_HOST, "");

        //先判断布隆过滤器是否存在
        boolean exist = stringExist.isExist(shortId);
        if (!exist) {
            return CommonResult.errorWithMsg("短链接不存在");
        }

        //先查询本地缓存
        String longUrl = LOCAL_CACHE.getIfPresent(shortId);
        if (StringUtils.isNotBlank(longUrl)) {
            return CommonResult.success(longUrl);
        }

        ShortUrlMappingPo shortUrlMappingPo = shortUrlMappingDao.getByShortUrl(shortId);
        if (Objects.nonNull(shortUrlMappingPo)) {
            return CommonResult.success(shortUrlMappingPo.getLongUrl());
        } else {
            log.error("数据存在问题，需要排查，shortUrl:{}", shortUrl);
            return CommonResult.errorWithMsg("短链接不存在");
        }
    }

}
