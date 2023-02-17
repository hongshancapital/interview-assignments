package com.zhangliang.suril.data;

/**
 * 数据存储管理
 *
 * @author zhang
 * @date 2021/12/01
 */
public interface DataStoreManage {

    /**
     * 存储网站信息
     *
     * @param originalUrl {@see String} 原始url
     * @param shortUrl {@see String} 处理后的短url
     */
    void store(String shortUrl, String originalUrl);

    /**
     * 获取原始域名
     *
     * @param shortUrl {@see String} 短网址
     * @return 返回原始Url {@see String} 信息
     */
    String fetch(String shortUrl);

    /**
     * 是否已经存在当前短地址
     *
     * @param shortUrl 短网址
     * @return boolean
     */
    boolean exists(String shortUrl);
}
