package com.example.demo.service;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: dsm
 * @Description: 数据存储服务类
 * @Date Create in 2021/12/23
 */
@Service
public class StorageService {

    @Resource
    private UrlService urlService;
    /**
     * 定义容器存储数据
     */
    private final static ConcurrentHashMap <String, String> hashMap=new ConcurrentHashMap <String, String>();

    /**
     * - 短域名读取接口：接受短域名信息，返回长域名信息
     *
     * @param areaName
     * @return
     */
    public String AreaNameRead( String areaName ) {
        return hashMap.get(areaName);

    }

    /**
     * - 短域名存储接口：接受长域名信息，返回短域名信息
     *
     * @param areaName
     * @return
     */
    public String AreaNameStorage( String areaName ) {
        String[] resUrl=this.urlService.shortUrl(areaName);
        for (String url : resUrl) {
            hashMap.put(url, areaName);
        }
        Random random=new Random();
        int pick=random.nextInt(4);
        return resUrl[pick];
    }
}
