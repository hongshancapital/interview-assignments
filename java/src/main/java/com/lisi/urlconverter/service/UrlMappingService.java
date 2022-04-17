package com.lisi.urlconverter.service;

/**
 * @description: 保存、获取域名映射关系的Service
 * @author: li si
 */
public interface UrlMappingService {
    /**
     * 保存原域名与转换后域名的映射关系
     * @param oriUrl 原域名
     * @param convertedUrl 转换后的域名
     */
    void saveMapping(String oriUrl, String convertedUrl);

    /**
     * 查找原域名
     * @param convertedUrl 查询值，转换后的短域名
     * @return 返回原域名信息
     */
    String getMapping(String convertedUrl);

    /**
     * 检查内存是否充足
     * @return
     */
    boolean isEnough();
}
