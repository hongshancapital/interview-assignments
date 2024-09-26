package com.example.shortlink.util;

/**
 * @author tianhao.lan
 * @date 2021-12-27.
 */
public class BinaryConversionUtil {

  static final String digits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";;

  static final int scale = 62;

  //数字转62进制
  public static String encode(long num) {
    StringBuilder sb = new StringBuilder();
    int remainder;
    while (num > scale - 1) {
      //对 scale 进行求余，然后将余数追加至 sb 中，由于是从末位开始追加的，因此最后需要反转字符串
      remainder = (int)(num % scale);
      sb.append(digits.charAt(remainder));
      //除以进制数，获取下一个末尾数
      num = num / scale;
    }
    sb.append(digits.charAt((int)num));
    return sb.reverse().toString();
  }

  //62进制转为数字
  public static long decode(String str) {
    //将 0 开头的字符串进行替换
    str = str.replace("^0*", "");
    long value = 0;
    char tempChar;
    int tempCharValue;
    for (int i = 0; i < str.length(); i++) {
      //获取字符
      tempChar = str.charAt(i);
      //单字符值
      tempCharValue = digits.indexOf(tempChar);
      //单字符值在进制规则下表示的值
      value += (long) (tempCharValue * Math.pow(scale, str.length() - i - 1));
    }
    return value;
  }


}
