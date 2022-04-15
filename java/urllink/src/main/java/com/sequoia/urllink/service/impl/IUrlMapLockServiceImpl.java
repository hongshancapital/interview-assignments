package com.sequoia.urllink.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sequoia.urllink.bean.UrlMapLock;
import com.sequoia.urllink.dao.UrlMapLockMapper;
import com.sequoia.urllink.service.IUrlMapLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author liuhai
 * @date 2022.4.15
 */
@Service
public class IUrlMapLockServiceImpl extends ServiceImpl<UrlMapLockMapper, UrlMapLock> implements IUrlMapLockService {
    @Autowired
    private UrlMapLockMapper urlMapLockMapper;

    private long minIdx = 15235584L;
    private long maxIdx = 56785000000L;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public long takeRange(long val) {
        long start = urlMapLockMapper.selectIdx();
        if (minIdx > start || maxIdx < start) {
            val = minIdx - start;
        }
        urlMapLockMapper.addIdx(val);
        return start;
    }
}
