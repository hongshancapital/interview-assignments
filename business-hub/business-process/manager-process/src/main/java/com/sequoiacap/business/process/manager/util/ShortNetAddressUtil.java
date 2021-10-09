package com.sequoiacap.business.process.manager.util;

import java.util.Random;

/**
 *@ClassName: ShortNetAddressUtil
 *@Description: ShortNetAddressUtil
 *@Author: xulong.wang
 *@Date 10/10/2021
 *@Version 1.0
 *
 */
public class ShortNetAddressUtil {

  private static char[] VALID_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
  private static Random random = new Random(System.currentTimeMillis());
  private static int length = 8;

  public static String generate(String url, int seed) {
    char[] sortUrl = new char[length];
    for (int i = 0; i < length; i++) {
      sortUrl[i] = VALID_CHARS[seed % VALID_CHARS.length];
      seed = random.nextInt(Integer.MAX_VALUE) % VALID_CHARS.length;
    }
    return new String(sortUrl);
  }

  public static String generate(String url) {
    String shortUrl = generate(url, random.nextInt(Integer.MAX_VALUE));
    return shortUrl;
  }

}

