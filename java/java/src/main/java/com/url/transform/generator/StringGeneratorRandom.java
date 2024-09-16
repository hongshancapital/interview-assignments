package com.url.transform.generator;

import com.url.transform.service.StringGenerator;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author xufei
 * @Description 随机字符串发生器的默认实现类
 * @date 2021/12/9 6:43 PM
 **/
public class StringGeneratorRandom implements StringGenerator {
  public static char[] VALID_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
  private static Random random = new Random(System.currentTimeMillis());
  private int length = 4;

  public StringGeneratorRandom() {
  }

  public StringGeneratorRandom(int length) {
    setLength(length);
  }

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  String generate(int seed) {
    char[] sortUrl = new char[length];
    for (int i = 0; i < length; i++) {
      sortUrl[i] = VALID_CHARS[seed % VALID_CHARS.length];
      seed = random.nextInt(Integer.MAX_VALUE) % VALID_CHARS.length;
    }
    return new String(sortUrl);
  }

  /**
   * 这里的实现不考虑url，直接生成随机字符串即可，这个算法如果容量比较大的时候，性能会变低，因此要根据使用情况选择合适的长度
   *
   * @param url
   * @return
   */
  public String generate(String url) {
    String shortUrl;
    shortUrl = generate(random.nextInt(Integer.MAX_VALUE));
    return shortUrl;
  }

}
