package com.url.transform.service;

/**
 * @author xufei
 * @Description
 * @date 2021/12/9 6:23 PM
 **/
public interface StringGenerator {
  String generate(String url);

  void setLength(int length);
}