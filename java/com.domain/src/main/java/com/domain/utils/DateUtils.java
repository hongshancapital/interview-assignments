package com.domain.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  日期 工具类
 * @author jacky
 * @version 1.0
 * @since 1.0
 */
public class DateUtils {

    public final static String DEFAUL_DATE_FORMATE = "yyyy-MM-dd HH:mm:ss";  //默认年月日格式

    /**
     * @description 按指定格式获取指定时间字符串
     * @param date
     *            待转换日期
     * @param patterns
     *            时间格式 e.g yyyy-MM-dd HH:mm:ss
     * @return 返回指定格式指定时间字符串
     */
    public static String getDateToString(Date date, String patterns) {
        if (date == null) {
            date = new Date();
        }
        SimpleDateFormat formatter = new SimpleDateFormat(patterns);
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * @description 按指定格式获取指定时间字符串
     * @param timestamp
     *            待转换日期
     * @param patterns
     *            时间格式 e.g yyyy-MM-dd HH:mm:ss
     * @return 返回指定格式指定时间字符串
     */
    public static String getTimestampToString(Timestamp timestamp, String patterns) {
        if(timestamp==null) return null;
        Date date=new Date(timestamp.getTime());
        String dateString = null;
        if (null != date) {
            SimpleDateFormat dateformat = new SimpleDateFormat(patterns);
            dateString = dateformat.format(date);
        }
        return dateString;
    }
}
