package com.zhucan.domain.infrastructure.utils;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;

/**
 * @author zhuCan
 * @description 域名转接工具
 * @since 2022/4/2 21:52
 */
@Slf4j
public class DomainMetathesisUtil {

  /**
   * 会使用到的常量
   */
  public static final long LONG_0X3FFFFFFF = 0x3FFFFFFF;
  public static final long LONG_0X0000003D = 0x0000003D;
  public static final int LONG_0XFF = 0xFF;
  public static final String MD5_KEY = "zhucan";

  public static String[] shortUrl(String url) {

    log.info("原始域名:{}", url);
    // 要使用生成 URL 的字符
    String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
        "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
        "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
        "W", "X", "Y", "Z"
    };
    // 对传入网址进行 MD5 加密
    String hex = md5ByHex(MD5_KEY + url);

    String[] resUrl = new String[Constants.INDEX_4];
    for (int i = Constants.INDEX_0; i < Constants.INDEX_4; i++) {
      // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
      String sTempSubString = hex.substring(i * Constants.INDEX_8, i * Constants.INDEX_8 + Constants.INDEX_8);
      // 这里需要使用 long 型来转换，因为 Integer.parseInt() 只能处理 31 位 , 首位为符号位 ,如果不用long，则会越界
      long lHexLong = LONG_0X3FFFFFFF & Long.parseLong(sTempSubString, Constants.INDEX_16);

      StringBuilder outChars = new StringBuilder();
      for (int j = Constants.INDEX_0; j < Constants.INDEX_6; j++) {
        // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
        long index = LONG_0X0000003D & lHexLong;
        // 把取得的字符相加
        outChars.append(chars[(int) index]);
        // 每次循环按位右移 5 位
        lHexLong = lHexLong >> Constants.INDEX_5;
      }
      // 把字符串存入对应索引的输出数组
      resUrl[i] = outChars.toString();
    }
    return resUrl;
  }

  /**
   * MD5加密(32位大写)
   *
   * @param src 原始字符串
   * @return 加密之后的字符串
   */
  private static String md5ByHex(String src) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] b = src.getBytes();
      md.reset();
      md.update(b);
      byte[] hash = md.digest();
      StringBuilder buffer = new StringBuilder();
      String stmp = "";
      for (byte value : hash) {
        stmp = Integer.toHexString(value & LONG_0XFF);
        if (stmp.length() == Constants.INDEX_1) {
          buffer.append("0").append(stmp);
        } else {
          buffer.append(stmp);
        }
      }
      return buffer.toString().toUpperCase();
    } catch (Exception e) {
      return "";
    }
  }

  /**
   * 下标常量
   */
  public static class Constants {
    public static final int INDEX_0 = 0;
    public static final int INDEX_1 = 1;
    public static final int INDEX_4 = 4;
    public static final int INDEX_5 = 5;
    public static final int INDEX_6 = 6;
    public static final int INDEX_8 = 8;
    public static final int INDEX_16 = 16;
  }
}
