package com.url.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 域名检查工具类
 * @Author jeckzeng
 * @Date 2022/4/30
 * @Version 1.0
 */
public class DomainCheckUtil {

   final static String DOMAIN_PATTERN = "^((http://)|(https://))?([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}(/)";

   public static boolean check(String urlLink){
       Pattern pattern = Pattern.compile(DOMAIN_PATTERN);
       Matcher matcher = pattern.matcher(urlLink);
       return matcher.find();
   }

}
