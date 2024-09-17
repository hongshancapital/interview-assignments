package com.jingdata.service;

import com.jingdata.core.Store;
import com.jingdata.core.StoreFactory;
import com.jingdata.exception.BizException;
import com.jingdata.exception.ExceptionCode;
import com.jingdata.util.ShortUrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @Author wuyonglei
 * @Date
 */
@Service
public class ShortUrlService {

    @Autowired
    private StoreFactory storeFactory;

    /**
     * 写入
     * @param realUrl
     * @return
     */
    public String write(String realUrl) throws BizException {
        Store store = storeFactory.getStore();
        if(store.overFlow()) {
            throw new BizException(ExceptionCode.OVER_LIMIT_ERROR);
        }
        String shortCode = ShortUrlUtils.generateShortCode(realUrl);
        store.write(shortCode, realUrl);
        return shortCode;
    }

    /**
     * 读取
     * @param shortCode
     * @return
     */
    public String read(String shortCode) throws BizException {
        Store store = storeFactory.getStore();
        String longUrl = store.read(shortCode);
        return longUrl;
    }
}
