package com.layne.interview.service;

/**
 * URL管理接口
 *
 * @author layne
 * @version UrlManage.java, v 0.1 2022/1/16 22:10 下午
 */
public interface UrlManageService {

    /**
     * 存储域名接口，存入长域名，返回短域名
     *
     * @param urlString 域名字符串
     * @return 短域名字符串
     */
    String storeUrl(String urlString);

    /**
     * 域名读取接口，根据输入的短域名获取存储的长域名
     *
     * @param shortUrl 短域名
     * @return 长域名String
     */
    String getUrl(String shortUrl);
}
