package com.url.transform.shorter;

import com.url.transform.service.ShorterGetter;

/**
 * @author xufei
 * @Description
 * @date 2021/12/10 9:58 AM
 **/
public class ShorterWithPeriodAndTimes implements ShorterGetter {
  private String shorter;
  private long period;
  private long times;
  public ShorterWithPeriodAndTimes() {
  }
  public ShorterWithPeriodAndTimes(String shorter, long period, long times) {
    setShorter(shorter);
    setTimes(times);
    setPeriod(period);
  }

  public long getTimes() {
    return times;
  }

  public void setTimes(long times) {
    this.times = times;
  }

  public String getShorter() {
    return shorter;
  }

  public void setShorter(String shorter) {
    this.shorter = shorter;
  }

  public long getPeriod() {
    return period;
  }

  public void setPeriod(long period) {
    this.period = period;
  }

}
