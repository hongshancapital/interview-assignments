package org.example.sgenerate.repository;

import org.example.sgenerate.model.UrlMappingInfo;

/**
 * 链接映射存储接口
 *
 * @author liuyadu
 */
public interface UrlMappingStore {

    /**
     * 保存路径
     *
     * @param info 链接映射信息
     */
    void storeUrl(UrlMappingInfo info);

    /**
     * 移除链接
     *
     * @param id
     */
    void removeUrl(String id);

    /**
     * 获取链接详情
     *
     * @param id
     * @return
     */
    UrlMappingInfo getUrl(String id);
}
