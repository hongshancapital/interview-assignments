package com.scdt.yulinfu.service.impl;

import com.scdt.yulinfu.service.DataStoreService;
import org.springframework.stereotype.Service;

/**
 * @author yulinfu
 * @description
 * @data 2021/10/15
 */
@Service
public class DataStoreServiceImpl implements DataStoreService {

    private static long CURRENT = 0L;

    /**
     * 获取当前映射
     *
     * @return
     */
    @Override
    synchronized public long getCurrent() {
        long current = CURRENT;
        CURRENT ++;
        return current;
    }
}
