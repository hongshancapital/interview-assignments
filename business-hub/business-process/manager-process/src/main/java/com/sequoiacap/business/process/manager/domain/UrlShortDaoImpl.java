package com.sequoiacap.business.process.manager.domain;

import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *@ClassName: UrlShortDaoImpl
 *@Description: UrlShortDaoImpl
 *@Author: xulong.wang
 *@Date 10/10/2021
 *@Version 1.0
 *
 */
@Component
public class UrlShortDaoImpl implements UrlShortDao{

  private Map<String, String> url2ShortUrl = new ConcurrentHashMap<String, String>();
  private Map<String, String> shortUrl2Url = new ConcurrentHashMap<String, String>();

  public void save(String url, String shortUrl) {
    //超过10万条数据，自动清除1000条，可做成异步处理
    if(url2ShortUrl.entrySet().size()>100000){
      Iterator<Map.Entry<String, String>> iterator = url2ShortUrl.entrySet().iterator();
      int i = 0;
      while (iterator.hasNext()) {
        i++;
        Map.Entry entry = iterator.next();
        String key = (String) entry.getKey();
        String value = (String) entry.getValue();
        url2ShortUrl.remove(key);
        shortUrl2Url.remove(value);
        if(i>1000){
          break;
        }
      }
    }
    url2ShortUrl.put(url, shortUrl);
    shortUrl2Url.put(shortUrl, url);
  }

  public String get(String shortUrl) {
    return shortUrl2Url.get(shortUrl);
  }

  public void clean(String url) {
    String sortUrl = url2ShortUrl.get(url);
    if (sortUrl != null) {
      url2ShortUrl.remove(url);
      shortUrl2Url.remove(sortUrl);
    }
  }

}
