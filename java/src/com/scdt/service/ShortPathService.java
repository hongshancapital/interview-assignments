package com.scdt.service;


public interface ShortPathService {
    /**
     * 短域名存储接口：接受长域名信息，返回短域名信息
     * @param longPath
     * @return
     */
    String getShortPath(String longPath);

    /**
     * 短域名读取接口：接受短域名信息，返回长域名信息
     * @param shortPath
     * @return
     */
    String getLongPath(String shortPath);
}


