package com.zhucan.domain.infrastructure.cache;

/**
 * @author zhuCan
 * @description
 * @since 2022/4/2 23:24
 */
public interface DomainCache {

  /**
   * 保存短域名与长域名的映射关系
   *
   * @param shortDomain 短域名
   * @param LongDomain  长域名
   */
  void save(String shortDomain, String LongDomain);

  /**
   * 通过短域名查找映射的长域名
   *
   * @param shortDomain 短域名
   * @return 长域名
   */
  String getLongDomain(String shortDomain);

}
