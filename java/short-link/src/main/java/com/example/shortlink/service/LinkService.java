package com.example.shortlink.service;

/**
 * @author tianhao.lan
 * @date 2021-12-27.
 */
public interface LinkService {



  /**
   * 长链接换短链接
   *
   * @param longLink
   * @return
   */
  String longChangShort(String longLink);

  /**
   * 短链接换长链接
   *
   * @param shortLink
   * @return
   */
  String shortChangeLong(String shortLink);

}
