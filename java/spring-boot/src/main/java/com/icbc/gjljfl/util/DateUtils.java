package com.icbc.gjljfl.util;


import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class DateUtils {

   private final String DATEFORMATER = "YYYY-MM-dd HH:mm:ss";

   private static String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

   //格式化时间
   private static String formateDate(String format, Date date) {
      if (StringUtils.isEmpty(format)) {
         if (null != date) {
            return LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
         }
         return LocalDate.now().toString();
      }
      return LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
   }


   /**
    * 计算请求话费的具体时间
    *
    * @param start
    * @param finish
    * @return
    */
   public static String computeTime(long start, long finish) {
      return (finish - start) / 1000 + "." + (finish - start) % 1000 + " 秒";
   }


   //时间格式转换
   public static Date converseTime(String time) {
      Date returnTime = null;
      try {
         returnTime = org.apache.commons.lang3.time.DateUtils.parseDate(time, "yyyy-MM-dd HH:mm:ss");
      } catch (Exception e) {
         e.printStackTrace();
      }
      return returnTime;
   }


   public static Date dateToStringToDate() {

      String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
      try {
         return org.apache.commons.lang3.time.DateUtils.parseDate(date, DATE_PATTERN);
      } catch (ParseException e) {
         e.printStackTrace();
      }
      return new Date();
   }

   public static String dateToString() {

      return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
   }

   public static String dateToString(Date time, String pattern) {

      TimeZone CST = TimeZone.getTimeZone("GMT+8");
      SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
      format.setTimeZone(CST);
      return format.format(time);


   }

   /**
    * 格式化时间
    *
    * @param dateTime
    * @return
    */
   public static Date StringToDate(String dateTime) {
      SimpleDateFormat d2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      try {
         Date date2 = d2.parse(dateTime);
         System.out.println("1");
         return date2;
      } catch (ParseException e) {
         e.printStackTrace();
      }
      System.out.println("2");
      return new Date();
   }

   /**
    * 简单判断是否是第二天
    * 通过比较格式化的日期字符串的值是否相等
    * 如果不相等 就认为不是同一天
    *
    * @param nowDate
    * @param otherDateString
    * @return
    */
   public static boolean simplateGetTimeDiffs(Date nowDate, String otherDateString) {
      //看自己的时间格式选择对应的转换对象
      String pattern = "yyyy-MM-dd HH:mm:ss";
      //String patttern_ = "yyyy-MM-dd";
      SimpleDateFormat sdf = new SimpleDateFormat(pattern);
      String nowDateString = sdf.format(nowDate);
      if (!org.apache.commons.lang3.StringUtils.isAnyBlank(otherDateString, nowDateString)) {

         String nowDay = nowDateString.substring(8, 10);
         String otherDay = otherDateString.substring(8, 10);
         if (!org.apache.commons.lang3.StringUtils.equals(nowDay, otherDay)) {
            //表明是第二天
            return true;
         } else {
            return false;
         }
      }
      return false;
   }

   public static void main(String[] args) {
      System.out.println(dateToStringToDate());
   }


}
