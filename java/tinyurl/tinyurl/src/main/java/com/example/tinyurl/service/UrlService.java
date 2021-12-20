package com.example.tinyurl.service;


/**
 * @author zhangtian
 * @version 1.0
 * @Project: UrlService
 * @Title: tinyurl
 * @Package com.example.tinyurl.service
 * @Description:长短链接互转服务接口
 * @date 2021/12/19 14:39
 */
public interface UrlService {

    /**
     * @Description:长链接转短链接方法
     * @param url 长链接url
     * @return 短链接url
     * @Version: 1.0
     * @author: zhangtian
     * @Date: 2021/12/19 14:42
     */
    String toTinyurl(String url);

    /**
     * @Description:短链接转长链接方法
     * @param url 短链接url
     * @return 长链接url
     * @Version: 1.0
     * @author: zhangtian
     * @Date: 2021/12/19 14:42
     */
    String fromTinyurl(String url);
}
