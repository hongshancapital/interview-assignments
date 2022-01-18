package com.url.transform.generator;

import com.url.transform.service.ShorterStorage;
import com.url.transform.service.StringGenerator;
import com.url.transform.service.UrlShorterGenerator;
import com.url.transform.shorter.ShorterWithPeriodAndTimes;
import org.springframework.stereotype.Service;

/**
 * @author xufei
 * @Description 用于生成指定长度的串,限制访问次数
 * @date 2021/12/10 9:55 AM
 **/
public class UrlShorterGeneratorLimitPeriodAndTimes implements UrlShorterGenerator<ShorterWithPeriodAndTimes> {
  private StringGenerator generator;
  private ShorterStorage<ShorterWithPeriodAndTimes> shorterStorage;
  /**
   * 有效时长，单位秒
   */
  private long period;
  /**
   * 最多使用次数
   */
  private long times;

  public StringGenerator getGenerator() {
    return generator;
  }

  public void setGenerator(StringGenerator generator) {
    this.generator = generator;
  }

  public ShorterStorage<ShorterWithPeriodAndTimes> getShorterStorage() {
    return shorterStorage;
  }

  public void setShorterStorage(ShorterStorage<ShorterWithPeriodAndTimes> shorterStorage) {
    this.shorterStorage = shorterStorage;
  }

  public long getTimes() {
    return times;
  }

  public void setTimes(long times) {
    this.times = times;
  }

  public long getPeriod() {
    return period;
  }

  public void setPeriod(long period) {
    this.period = period;
  }

  public ShorterWithPeriodAndTimes generate(String url) {
    String shorter = generator.generate(url);
    while (shorterStorage.get(shorter) != null) {
      shorter = generator.generate(url);
    }
    ShorterWithPeriodAndTimes shorterWithPeriodAndTimes = new ShorterWithPeriodAndTimes(shorter, period, times);
    shorterStorage.save(url, shorterWithPeriodAndTimes);
    return shorterWithPeriodAndTimes;
  }

}
