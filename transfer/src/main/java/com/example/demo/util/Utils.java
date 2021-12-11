package com.example.demo.util;

import org.apache.commons.lang3.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {
   private static String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_-";
   private static int scale = 64;
   
   public static String numToStr(int num, int length) {
	   StringBuilder builder = new StringBuilder();
	   
	   long longNum = Integer.toUnsignedLong(num);
   	   log.info("int to long: " + num + "->" + longNum);
	   
	   int remainder = 0; 
	   while (longNum > scale - 1) {
		   remainder = Long.valueOf(longNum % scale).intValue();
		   builder.append(chars.charAt(remainder));
		   
		   longNum = longNum / scale;
	   }
	   
	   builder.append(chars.charAt(Long.valueOf(longNum).intValue()));
	   String value = builder.reverse().toString();
	   return StringUtils.rightPad(value, length, '0');
   }
}
