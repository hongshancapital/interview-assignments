package com.zz.service.impl;

import com.zz.exception.BusinessException;
import com.zz.exception.code.ShortUrlErrorCodeEnum;
import com.zz.generator.ShortUrlGenerator;
import com.zz.lock.GeneratorLock;
import com.zz.lock.LocalCacheLock;
import com.zz.param.inparam.ShortUrlQueryParam;
import com.zz.param.inparam.ShortUrlStoreParam;
import com.zz.param.outparam.ShortUrlDTO;
import com.zz.service.ShortUrlService;
import com.zz.store.ShortUrlStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 短链接服务
 *
 * @author zz
 * @version 1.0
 * @date 2022/3/31
 */
@Service
public class ShortUrlServiceImpl implements ShortUrlService {
    @Autowired
    private ShortUrlGenerator shortUrlGenerator;

    @Autowired
    private ShortUrlStore shortUrlStore;

    /**
     * 存储锁
     */
    private GeneratorLock generatorLock = new LocalCacheLock();

    /**
     * 生成短链接，并且存储在本次内存
     * 1、进行url一些预处理，原始的url需要参数去除
     * 2、判断url是否已经存在，通过map直接判断，如果存在，直接返回
     * 3、url不存在，根据自增序列生成器生成shortcode
     * 4、加锁更新
     * 5、存放shortcode到url的映射,url到shortcode的映射到内存缓存中，
     *
     * @return
     */
    public ShortUrlDTO storeShortUrl(ShortUrlStoreParam shortUrlStoreParam) throws BusinessException {
        //1、进行url一些预处理，原始的url需要参数去除
        String url = cleanParamUrl(shortUrlStoreParam.getHttpUrl());

        ShortUrlDTO shortUrlDTO = new ShortUrlDTO();
        shortUrlDTO.setOriginHttpUrl(shortUrlStoreParam.getHttpUrl());

        //2、判断url是否已经存在，通过map直接判断，如果存在，直接返回
        String shortCode = shortUrlStore.getShortCodeByUrl(url);
        if (shortCode != null) {
            shortUrlDTO.setShortCode(shortCode);
            return shortUrlDTO;
        }

        //3、url不存在，根据自增序列生成器生成shortcode
        shortCode = shortUrlGenerator.convertToCode(url);
        shortUrlDTO.setShortCode(shortCode);

        try {
            generatorLock.lock(url);
            shortUrlStore.storeMapping(url, shortCode);
            return shortUrlDTO;
        } finally {
            generatorLock.unlock(url);
        }
    }

    /**
     * 去除参数
     *
     * @param url
     * @return
     */
    private String cleanParamUrl(String url) {
        int index = url.indexOf("?");
        if (index > 0) {
            return url.substring(0, index);
        } else {
            return url;
        }
    }

    /**
     * 获取短链接
     * 1、对于shortcode，进行基础校验
     * 2、存在，查询map结果，如果不存在，过期报错；存在直接返回结果
     *
     * @return
     */
    public ShortUrlDTO queryShortUrl(ShortUrlQueryParam shortUrlQueryParam) throws BusinessException {
        //1、对于shortcode，进行基础校验
        if (!shortUrlGenerator.isValid(shortUrlQueryParam.getShortCode())) {
            throw new BusinessException(ShortUrlErrorCodeEnum.SU_005);
        }
        //2、存在，查询map结果，如果不存在，过期报错；存在直接返回结果
        String url = shortUrlStore.getUrlByShortCode(shortUrlQueryParam.getShortCode());
        if (url == null) {
            throw new BusinessException(ShortUrlErrorCodeEnum.SU_003);
        }
        ShortUrlDTO shortUrlDTO = new ShortUrlDTO();
        shortUrlDTO.setOriginHttpUrl(url);
        return shortUrlDTO;
    }
}
