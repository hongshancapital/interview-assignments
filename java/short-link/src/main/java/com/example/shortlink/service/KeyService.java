package com.example.shortlink.service;

/**
 * @author tianhao.lan
 * @date 2021-12-27.
 */
public interface KeyService {

  int BIT = 7;

  /**
   * 获取唯一ID.
   *
   * @return 唯一ID.
   */
  long getUniqueId();
}
