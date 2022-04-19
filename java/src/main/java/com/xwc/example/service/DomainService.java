package com.xwc.example.service;

/**
 * 类描述：ShortDomain 的业务实现接口
 * 作者：徐卫超 (cc)
 * 时间 2022/4/13 18:11
 */
public interface DomainService {


    /**
     * 通过一个长域名返回一个短域名
     *
     * @param lengthDomain 长域名地址
     * @return 返回一个短域名信息
     */
    String getShortDomain(String lengthDomain);


    /**
     * 通过短域名查找一个长域名信息
     *
     * @param shortDomain 短域名地址
     * @return 长域名信息
     */
    String findLengthDomain(String shortDomain);


    /**
     * 清除系统容量 用于测试
     */
    void clear();


}
