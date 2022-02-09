package cn.sequoiacap.interview.xurl.util;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/** 数值转换工具类 */
public class BaseConvertor {
  private static final char[] BASE62_CHARS =
      "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
  private static final Map<Character, Integer> CHARS_TO_NUM_62 = new HashMap<>(62);
  private static final long BASE62_8BIT_MAX = (long) (Math.pow(62, 8) - 1);

  // 初始化 CHARS_TO_NUM_62
  static {
    IntStream.range(0, 62)
        .forEach(
            i -> {
              CHARS_TO_NUM_62.put(BASE62_CHARS[i], i);
            });
  }

  /**
   * 转62进制字符
   *
   * @param n 待转换的数
   * @return 转换后的字符串
   */
  public static String to62(long n) {
    if (n < 0 || n > BASE62_8BIT_MAX) {
      throw new NumberFormatException("out of range");
    }
    // 这里结合最多8位，优化了实现
    char[] res = "........".toCharArray();
    short i = 7;
    while (n > 0) {
      res[i] = BASE62_CHARS[(int) (n % 62)];
      n /= 62;
      i -= 1;
    }
    int count = 7 - i;
    return count == 0 ? "0" : new String(res, i + 1, count);
  }

  /**
   * 将62进制转为long类型
   *
   * @param s 待转换的62进制字符串
   * @return 转换后的long型数值
   */
  public static long from62(String s) {
    int l = s.length();
    if (l > 8) {
      throw new NumberFormatException("out of range");
    }
    long res = 0;
    for (int i = l - 1, y = 0; i > -1; i--, y++) {
      Integer temp = CHARS_TO_NUM_62.get(s.charAt(i));
      if (temp == null) {
        throw new NumberFormatException("invalid num");
      }
      res += temp * Math.pow(62, y);
    }
    return res;
  }
}
