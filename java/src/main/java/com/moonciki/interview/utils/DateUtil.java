package com.moonciki.interview.utils;

import com.moonciki.interview.commons.enums.ResponseEnum;
import com.moonciki.interview.commons.exception.CustomException;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * 时间工具类
 *
 * @author admin
 */
public class DateUtil {
	public static final String date_time_format = "yyyy-MM-dd HH:mm:ss";
    public static final String day_format = "yyyy-MM-dd";

    public static final String time_format_short = "yyyyMMddHHmmss";
    public static final String day_format_short = "yyyyMMdd";

    /**
     * 毫秒时间戳 + 随机数
     *
     * @return string 时间戳
     */
    public static String getNanoRandom() {
        long time = System.nanoTime();
        Random random = new Random();
        time += random.nextInt(999999999);
        return String.valueOf(time);
    }

    /**
     * 获得当前日期
     *
     * @param formatType
     * @return
     */
    public static String getNowTime(String formatType) {

        Date date = new Date();

        SimpleDateFormat format = new SimpleDateFormat(formatType);

        String resultDay = format.format(date);

        return resultDay;
    }

    /**
     * 转换String 为 date
     *
     * @param dateStr
     * @param formatType
     * @return
     * @throws Exception
     */
    public static Date parseDate(String dateStr, String formatType){

        SimpleDateFormat format = new SimpleDateFormat(formatType);

        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            throw CustomException.createException(e);
        }

        return date;
    }

    /**
     * 将 2014-01-11 这样的日期转换成 20140211
     *
     * @param day
     * @return
     */
    public static long parseDayToLong(String day) {
        long longDay = new Long(day.replaceAll("-", ""));

        return longDay;
    }

    /**
     * 获得字符串日期时间戳
     *
     * @param day
     * @param formatType
     * @return
     * @throws Exception
     */
    public static long getDateTime(String day, String formatType) throws Exception {

        Date date = DateUtil.parseDate(day, formatType);

        long timeLong = date.getTime();

        return timeLong;
    }

    /**
     * 日期转字符串
     *
     * @param date
     * @param formatType
     * @return
     */
    public static String dateToString(Date date, String formatType) {
        SimpleDateFormat format = new SimpleDateFormat(formatType);

        String resultDay = format.format(date);
        return resultDay;
    }

    /**
     *  excel字符串转DATE
     * @param dateStr
     * @return
     * @throws Exception
     */
    public static Date strongStrToData(String dateStr) throws Exception{
        String[] formatArray = {"y", "M", "d", "H", "m", "s"};
        //yyyy-MM-dd HH:mm:ss
        String[] splitArray = {"-", "-", " ", ":", ":", ""};
        int[] partSizeArray = {4, 2, 2, 2, 2, 2};

        char[] charArray = dateStr.toCharArray();
        StringBuilder dateStrBuilder = new StringBuilder();
        StringBuilder formatBuilder = new StringBuilder();
        int part = 0;

        int nowSize = 0;
        for(char oneChar : charArray){
            int partSize = partSizeArray[part];

            if(part >= (formatArray.length - 1) && nowSize >= partSize){
                throw CustomException.createException(ResponseEnum.sys_error.info("日期格式化错误"));
            }
            if(Character.isDigit(oneChar)){
                if(nowSize >= partSize){
                    throw CustomException.createException(ResponseEnum.sys_error.info("日期格式化错误"));
                }
                dateStrBuilder.append(oneChar);
                String formatPart = formatArray[part];
                formatBuilder.append(formatPart);

                nowSize ++;

            }else{
                String spliter = splitArray[part];
                dateStrBuilder.append(spliter);
                formatBuilder.append(spliter);

                nowSize = 0;
                part++;
            }
        }

        String newDateStr = dateStrBuilder.toString();
        String formatType = formatBuilder.toString();
        Date date = parseDate(newDateStr, formatType);

        return date;
    }

    /**
     * excel常规格式日期转java日期
     * @param dateObj
     * @return
     */
    public static Date strongDoubleToDate(Double dateObj){
        throw CustomException.createException(ResponseEnum.sys_error.info("un support yet"));
    }

    public static Date parseCellDate(Object dateObj) throws Exception{
        Date result = null;
        if (dateObj == null){

        }else if(dateObj instanceof String){
            String dateStr = (String) dateObj;

            if(StringUtils.isNotBlank(dateStr)) {
                result = strongStrToData((String) dateObj);
            }
        }else if(dateObj instanceof BigDecimal){
            Double dateDouble = ((BigDecimal)dateObj).doubleValue();
            result = strongDoubleToDate(dateDouble);
        }else{
            //error
            throw CustomException.createException(ResponseEnum.sys_error.info("日期格式化错误"));
        }

        return result;
    }

    /**
     * 把时间戳转换成字符串格式
     * @param dateFormat 格式
     * @param timeMillis 时间戳
     * @return
     */
    public static String transferLongToDate(String dateFormat, Long timeMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(timeMillis);
        return sdf.format(date);
    }

    /**
     * 日期计算
     * @param date   时间
     * @param type   Calendar.DAY_OF_MONTH
     * @param number 可以为负数
     * @return
     */
    public static Date addTime(Date date, int type, int number){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int timeValue = calendar.get(type);

        timeValue = timeValue + number;

        calendar.set(type, timeValue);

        Date resultDate = calendar.getTime();

        return resultDate;
    }


    public static void main(String[] args) {

        try {
            Long  time = 600624000000L;
            String s = transferLongToDate(day_format, time);
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("####LastHourDay : ");
    }

}
