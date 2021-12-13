package com.example.demo.util;

public class Utils {
   private static String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_-";
   private static int scale = 64;
   
   /*
    * 数字到字符串
    * 
    * @param num 数字，大于等于0，如果小于0被转成正整数
    */
   public static String numToStr(long num) {
	   StringBuilder builder = new StringBuilder();
	   
	   num = Math.abs(num);
	   int remainder = 0; 
	   while (num > scale - 1) {
		   remainder = Long.valueOf(num % scale).intValue();
		   builder.append(chars.charAt(remainder));
		   
		   num = num / scale;
	   }
	   
	   builder.append(chars.charAt(Long.valueOf(num).intValue()));
	   return builder.reverse().toString();
   }
}
