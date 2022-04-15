package com.sequoia.urllink.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sequoia.urllink.bean.UrlMapLock;

/**
 * @author liuhai
 * @date 2022.4.15
 */
public interface IUrlMapLockService extends IService<UrlMapLock> {
    long takeRange(long val);
}
