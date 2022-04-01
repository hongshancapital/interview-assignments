package com.zz.service;

import com.zz.exception.BusinessException;
import com.zz.param.inparam.ShortUrlQueryParam;
import com.zz.param.inparam.ShortUrlStoreParam;
import com.zz.param.outparam.ShortUrlDTO;

/**
 * 短链接服务
 *
 * @author zz
 * @version 1.0
 * @date 2022/3/31
 */
public interface ShortUrlService {
    /**
     * 生成短链接，并且存储在本次内存
     *
     * @return
     */
    ShortUrlDTO storeShortUrl(ShortUrlStoreParam shortUrlStoreParam) throws BusinessException;

    /**
     * 获取短链接
     *
     * @return
     */
    ShortUrlDTO queryShortUrl(ShortUrlQueryParam shortUrlQueryParam) throws BusinessException;
}
