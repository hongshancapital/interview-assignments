package com.url.transform.storage;

import com.url.transform.service.ShorterGetter;
import com.url.transform.service.ShorterStorage;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xufei
 * @Description
 * @date 2021/12/9 6:41 PM
 **/
public class ShorterStorageMemory<T extends ShorterGetter> implements ShorterStorage<T> {
  /**
   * 存储shorter,url
   */
  public static final Map<ShorterGetter, String> shorterMap = new ConcurrentHashMap<ShorterGetter, String>();
  /**
   * 存储url,shorter
   */
  public static final Map<String, ShorterGetter> urlMap = new ConcurrentHashMap<String, ShorterGetter>();
  /**
   * 存储shorter.shorter,shorter
   */
  public static final Map<String, ShorterGetter> shorterUrlMap = new ConcurrentHashMap<String, ShorterGetter>();

  public String get(String shorterKey) {
    ShorterGetter shorter = shorterUrlMap.get(shorterKey);
    if (shorter != null) {
      return shorterMap.get(shorter);
    }
    return null;
  }

  public String getShorter(String url){
    ShorterGetter shorter = urlMap.get(url);
    if (shorter != null) {
      return shorter.getShorter();
    }
    return null ;
  }

  public void clean(String url) {
    ShorterGetter shorter = urlMap.get(url);
    if (shorter != null) {
      urlMap.remove(url);
      shorterMap.remove(shorter);
      shorterUrlMap.remove(shorter.getShorter());
    }
  }

  public void cleanShorter(String shorterKey) {
    ShorterGetter shorter = shorterUrlMap.get(shorterKey);
    if (shorter != null) {
      urlMap.remove(shorterMap.get(shorter));
      shorterMap.remove(shorter);
      shorterUrlMap.remove(shorter.getShorter());
    }

  }

  public void save(String url, T shorter) {
    urlMap.put(url, shorter);
    shorterMap.put(shorter, url);
    shorterUrlMap.put(shorter.getShorter(), shorter);
  }

  public void clean() {
    shorterMap.clear();
    shorterUrlMap.clear();
    urlMap.clear();
  }
}