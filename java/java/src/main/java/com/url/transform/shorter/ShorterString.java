package com.url.transform.shorter;

import com.url.transform.service.ShorterGetter;

/**
 * @author xufei
 * @Description
 * @date 2021/12/9 6:46 PM
 **/
public class ShorterString implements ShorterGetter {
  private String shorter;

  public ShorterString() {
  }

  public ShorterString(String shorter) {
    setShorter(shorter);
  }

  public String getShorter() {
    return shorter;
  }

  public void setShorter(String shorter) {
    this.shorter = shorter;
  }


}
