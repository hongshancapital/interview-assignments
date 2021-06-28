package com.sequoia.shorturl.service.impl;

import com.sequoia.shorturl.common.SequoiaResponse;
import com.sequoia.shorturl.service.ShortUrlService;
import com.sequoia.shorturl.util.ShortUrlUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;import java.util.List;

/***
 *
 *@短域名实现类
 *
 *@Author xj
 *
 *@Date 2021/6/27 16:18
 *
 *@version v1.0
 *
 */
@Service
@Slf4j
public class ShortUrlServiceImpl implements ShortUrlService {

    //caffeine 本地缓存，防止内存溢出
    @Autowired
    Cache<String, String> shortCache;  //
    @Autowired
    Cache<String, String> originalCache; //

    /**
     * 根据原地址返回短地址
     * @param originalUrl
     * @return
     */
    @Override
    public SequoiaResponse getShortUrl(String originalUrl)
    {
        try{
             log.info("request originalUrl:{}",originalUrl);
             if(StringUtils.isEmpty(originalUrl)){
                 return new SequoiaResponse().responseError("参数错误,不能为空!");
             }
             String shortUrl=shortCache.getIfPresent(originalUrl);
             if(StringUtils.isEmpty(shortUrl)){
             //没命中缓存，重新生成
                 shortUrl=ShortUrlUtil.getBatchShortUuidList().get(0);//使用批量发号器
                 shortCache.put(originalUrl,shortUrl); //放入原域名跟短域名映射
                 originalCache.put(shortUrl,originalUrl);//放入短域名跟原域名映射
                 if(originalCache.getIfPresent(shortUrl)!=null){
                      ShortUrlUtil.getBatchShortUuidList().remove(0);//用过的删除
                     //System.out.println(ShortUrlUtil.getBatchShortUuidList().size());
                }
             }
             log.info("response shortUrl:{}",shortUrl);
             return new SequoiaResponse(0, "success", shortUrl);
          } catch(Exception e) {
              log.error("ShortUrlServiceImpl.getShortUrl()，根据原地址返回短地址出现异常，异常信息为：\n", e);
              return new SequoiaResponse().responseError("处理失败");
          }
    }
    /**
     * 根据短地址返回原地址
     * @param originalUrl
     * @return
     */
    @Override
    public SequoiaResponse getOriginalUrl(String shortUrl)
    {
        try{
             log.info("request shortUrl:{}",shortUrl);
             if(StringUtils.isEmpty(shortUrl)){
                return new SequoiaResponse().responseError("参数错误,不能为空!");
             }
             String originalUrl=originalCache.getIfPresent(shortUrl);
             log.info("reponse originalUrl:{}",originalUrl);
             return new SequoiaResponse(0, "success", originalUrl);
        } catch(Exception e) {
           log.error("ShortUrlServiceImpl.getOriginalUrl()，短地址返回原地址出现异常，异常信息为：\n", e);
           return new SequoiaResponse().responseError("处理失败");
        }
    }
}
