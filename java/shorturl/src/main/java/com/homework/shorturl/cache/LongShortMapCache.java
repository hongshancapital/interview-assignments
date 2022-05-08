package com.homework.shorturl.cache;

import com.homework.shorturl.model.LongShortMapModel;

import java.util.Optional;

public interface LongShortMapCache {
    /**
     * 增加或更新映射
     *
     * @param mapModel
     *         长短链映射
     */
    void addOrUpdate(LongShortMapModel mapModel);

    /**
     * 删除映射
     *
     * @param mapModel
     *         长短链映射
     */
    void delete(LongShortMapModel mapModel);

    /**
     * 根据长链查询缓存
     *
     * @param url 长链
     * @return 长短链映射
     */
    Optional<LongShortMapModel> getByLong(String url);

    /**
     * 根据短链查询缓存
     *
     * @param url 短链
     * @return 长短链映射
     */
    Optional<LongShortMapModel> getByShort(String url);

    /**
     * 查询缓存大小
     *
     * @return 缓存大小
     */
    int getCacheSize();

    /**
     * 查询已使用缓存数量
     *
     * @return 已缓存映射数量
     */
    int getInUsedCacheSize();

    /**
     * 缓存是否已满
     *
     * @return true if 缓存已满， or false
     */
    default boolean isFull(){
        return getCacheSize() == getInUsedCacheSize();
    }
}
