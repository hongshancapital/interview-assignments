package com.url.transform.generator;

import com.url.transform.service.ShorterStorage;
import com.url.transform.service.StringGenerator;
import com.url.transform.service.UrlShorterGenerator;
import com.url.transform.shorter.ShorterString;
import org.springframework.stereotype.Service;

/**
 * @author xufei
 * @Description 用于生成指定长度的串
 * @date 2021/12/9 6:45 PM
 **/
public class UrlShorterGeneratorSimple implements UrlShorterGenerator<ShorterString> {

  private StringGenerator generator;
  private ShorterStorage<ShorterString> shorterStorage;

  public ShorterStorage<ShorterString> getShorterStorage() {
    return shorterStorage;
  }

  public void setShorterStorage(ShorterStorage<ShorterString> shorterStorage) {
    this.shorterStorage = shorterStorage;
  }

  public StringGenerator getGenerator() {
    return generator;
  }

  public void setGenerator(StringGenerator generator) {
    this.generator = generator;
  }

  public ShorterString generate(String url) {
    if(shorterStorage!=null && shorterStorage.getShorter(url)!= null ){
      ShorterString oldShorter = new ShorterString(shorterStorage.getShorter(url));
      return oldShorter;
    }else{
      String shorter = generator.generate(url);
      while (shorterStorage.get(shorter) != null) {
        shorter = generator.generate(url);
      }
      ShorterString newShorter = new ShorterString(shorter);
      shorterStorage.save(url, newShorter);
      return newShorter;
    }
  }
}
