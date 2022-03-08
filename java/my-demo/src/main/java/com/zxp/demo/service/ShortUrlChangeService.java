package com.zxp.demo.service;

/**
 * @description: 长链接转短链接服务接口
 * @author: zxp
 * @date: Created in 2021/12/13 12:52
 */
public interface ShortUrlChangeService {

      /**
       * 长链接转化为短链接接口
       * @author zxp
       * @date 2021/12/13 12:53
       * @param longUrl  长链接地址
       * @return java.lang.String 短链接地址
       */
      String conversionShortUrl(String longUrl);

      /**
       *
       * @author zxp
       * @date 2021/12/13 15:23
       * @param shortUrl  短链接地址
       * @return java.lang.String
       */
      String conversionLongUrl(String shortUrl);
}
