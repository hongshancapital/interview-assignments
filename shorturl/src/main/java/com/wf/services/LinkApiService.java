package com.wf.services;

import org.springframework.stereotype.Service;

/**
 * @链接服务类接口
 */
@Service
public interface LinkApiService {

    /**
     * 通过长链接获取短链接
     * @param link
     * @return 短链接
     */
    java.lang.String toShort(String link);

    /**
     * 通过短链接获取长链接
     * @param link 短链接
     * @return 长链接
     */
    java.lang.String toLong(String link);
}
