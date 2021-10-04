package com.sequoia.interview.shorturl.service;

public interface ShortUrlService {
  /**
   * 给定网址生成对应的短网址
   * 
   * @param url 原始url
   * @return 返回短链接
   */
  public String genShortUrl(String url);

  /**
   * 给定 短网址返回对应的网址
   * 
   * @param shortUrl
   * @return
   */
  public String getUrl(String shortUrl);

  /**
   * 实现 62进制加1
   * @param curValue
   * @return
   */
  public char[] plusOne(char[] curValue);

  /**
   * 设置curVule，新的短地址生成在curValue基础上加1
   * @param curValue
   */
  public void setCurValue(String curValue);

}
