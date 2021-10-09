package com.sequoiacap.business.process.manager.service;

import com.sequoiacap.business.process.manager.domain.UrlShortDao;
import com.sequoiacap.business.process.manager.util.ShortNetAddressUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *@ClassName: UrlShortServiceImpl
 *@Description: UrlShortServiceImpl
 *@Author: xulong.wang
 *@Date 10/10/2021
 *@Version 1.0
 *
 */
@Service
public class UrlShortServiceImpl implements UrlShortService {

  @Autowired
  private UrlShortDao urlShortDao;

  public String get(String url) {
    String sortUrl = urlShortDao.get(url);
    return sortUrl;
  }

  public String generate(String url) {
    String shortUrl = ShortNetAddressUtil.generate(url);
    while (urlShortDao.get(shortUrl) != null) {
      shortUrl = ShortNetAddressUtil.generate(url);
    }
    urlShortDao.save(url, shortUrl);
    return shortUrl;
  }



}
