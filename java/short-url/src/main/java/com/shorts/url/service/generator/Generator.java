package com.shorts.url.service.generator;

/**
 * <p>
 * 生成短链接
 * </p>
 *
 * @author WangYue
 * @date 2022/3/21 18:07
 */
public interface Generator {

    /**
     * 生成短链接
     *
     * @param longUrl 长链接
     * @return -
     */
    String generate(String longUrl);

    /**
     * 根据短链接获取原始长链接
     *
     * @param shortUrl 短链接
     * @return -
     */
    String get(String shortUrl);
}
