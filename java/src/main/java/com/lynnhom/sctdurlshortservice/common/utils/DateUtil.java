package com.lynnhom.sctdurlshortservice.common.utils;

import com.lynnhom.sctdurlshortservice.common.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @description: 日期工具类
 * @author: Lynnhom
 * @create: 2021-11-08 20:53
 **/
@Slf4j
public class DateUtil {
    public static final int QUARTER_DATE_LENGTH = 6;
    public static final String[] MONTHS = new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    public static final String[] QUARTERS = new String[] {"Q1", "Q2", "Q3", "Q4"};

    public enum Format {
        /**
         * 日期格式枚举类
         */
        YYYY("yyyy"),
        YYYYMM("yyyyMM"),
        MM("MM"),
        HH("HH"),
        MINUTE("mm"),
        HHMM("HH:mm"),
        MMDD("MMdd"),
        YYYYMMEEE("yyyyMMEEE"),
        YYMMdd("yyMMdd"),
        YYYYMMDD("yyyyMMdd"),
        YYMMDDHH("yyMMddHH"),
        YYMMDDHHMMSS("yyMMddHHmmss"),
        YYYYMMDDHH("yyyyMMddHH"),
        YYYYMMDDHHMM("yyyyMMddHHmm"),
        YYYYMMDDHHMMSS("yyyyMMddHHmmss"),
        YYYYMMDDHH_MM_SS("yyyyMMdd HH:mm:ss"),
        HYPHEN_YYYYMM("yyyy-MM"),
        HYPHEN_YYYYMMEEE("yyyy-MM-EEE"),
        HYPHEN_YYYYMMDD("yyyy-MM-dd"),
        HYPHEN_YYYYMMDDHH("yyyy-MM-dd HH"),
        HYPHEN_YYYYMMDDHHMM("yyyy-MM-dd HH:mm"),
        HYPHEN_YYYYMMDDHHMMSS("yyyy-MM-dd HH:mm:ss"),
        HYPHEN_MMDDHHMM("MM-dd HH:mm"),
        HYPHEN_MMDD("MM-dd"),
        SLANT_YYYYMM("yyyy/MM"),
        SLANT_YYYYMMEEE("yyyy/MM/EEE"),
        SLANT_YYYYMMDD("yyyy/MM/dd"),
        SLANT_YYYYMMDDHH("yyyy/MM/dd HH"),
        SLANT_YYYYMMDDHHMM("yyyy/MM/dd HH:mm"),
        SLANT_YYYYMMDDHHMMSS("yyyy/MM/dd hh:mm:ss"),
        ES_YYYYMMDD("yyyy.MM.dd"),
        HH_MM_SS("HH:mm:ss"),
        YYYYNIANMMYUE("yyyy年MM月");

        private final String format;

        private Format(String format) {

            this.format = format;
        }

        public String getFormat() {

            return format;
        }

        public int getType() {

            String[] names = name().split("_");
            String name = null;
            if (names.length > 1) {
                name = names[1];
            } else {
                name = names[0];
            }
            String pref = ".*";
            if (name.matches(pref + "Y+$")) {
                return Calendar.YEAR;
            } else if (name.matches(pref + "M+$")) {
                return Calendar.MONTH;
            } else if (name.matches(pref + "E+$")) {
                return Calendar.WEEK_OF_YEAR;
            } else if (name.matches(pref + "D+$")) {
                return Calendar.DATE;
            } else if (name.matches(pref + "H+$")) {
                return Calendar.HOUR;
            } else if (name.matches(pref + "H+M+$")) {
                return Calendar.MINUTE;
            } else if (name.matches(pref + "S+$")) {
                return Calendar.SECOND;
            } else {
                throw new IllegalArgumentException("Illegal Date Type");
            }
        }
    }

    /**
     * 根据字符串获取格式类型
     *
     * @param pattern   字符串格式
     * @return          格式类型
     */
    public static int getFormatType(String pattern) {
        for (Format format : Format.values()) {
            if (format.getFormat().equals(pattern)) {
                return format.getType();
            }
        }
        return -1;
    }

    /**
     * 获取日期中的天数
     *
     * @param date  日期
     * @return      月的第几天
     */
    public static int getDay(Date date) {
        String month = new SimpleDateFormat("dd").format(date);
        return Integer.parseInt(month);
    }

    /**
     * 获取日期中的月份
     *
     * @param date  日期
     * @return      月份
     */
    public static int getMonth(Date date) {
        String month = new SimpleDateFormat("MM").format(date);
        return Integer.parseInt(month);
    }

    /**
     * 获取某一年
     *
     * @param date  日期
     * @return      年
     */
    public static int getYear(Date date) {
        String year = new SimpleDateFormat("yyyy").format(date);
        return Integer.parseInt(year);
    }

    /**
     * return current date
     *
     * @return current date
     */
    public static Date now() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * return current date
     *
     * @return current date
     */
    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 修改并返回新日期
     *
     * @param date      日期
     * @param amount    修改量
     * @param field     修改属性
     * @return          新日期
     */
    public static Date add(Date date, int amount, int field) {
        if (field == Calendar.YEAR) {
            return DateUtils.addYears(date, amount);
        } else if (field == Calendar.MONTH) {
            return DateUtils.addMonths(date, amount);
        } else if (field == Calendar.DATE) {
            return DateUtils.addDays(date, amount);
        } else if (field == Calendar.WEEK_OF_MONTH || field == Calendar.WEEK_OF_YEAR) {
            return DateUtils.addWeeks(date, amount);
        } else if (field == Calendar.HOUR || field == Calendar.HOUR_OF_DAY) {
            return DateUtils.addHours(date, amount);
        } else if (field == Calendar.SECOND) {
            return DateUtils.addSeconds(date, amount);
        } else if (field == Calendar.MINUTE) {
            return DateUtils.addMinutes(date, amount);
        } else {
            throw new IllegalArgumentException("Un support date type : " + field);
        }
    }

    /**
     * 比较两个日期差多少时间单位
     *
     * @param left  日期1
     * @param right 日期2
     * @param unit  单位
     * @return      差值
     */
    public static long diff(Date left, Date right, TimeUnit unit) {
        return unit.convert(left.getTime() - right.getTime(), TimeUnit.MILLISECONDS);
    }

    /**
     * 比较两个日期差多少时间单位，日期1延迟delay个单位
     *
     * @param left  日期1
     * @param delay 延迟时间
     * @param right 日期2
     * @param unit  单位
     * @return      差值
     */
    public static long diff(Date left, long delay, Date right, TimeUnit unit) {
        return unit.convert(left.getTime() + TimeUnit.MILLISECONDS.convert(delay, unit) - right.getTime(), TimeUnit.MILLISECONDS);
    }

    public static Date getStarting(Date date, int field) {
        if (field == Calendar.YEAR) {
            return DateUtils.truncate(date, Calendar.YEAR);
        } else if (field == Calendar.MONTH) {
            return DateUtils.truncate(date, Calendar.MONTH);
        } else if (field == Calendar.DATE) {
            return DateUtils.truncate(date, Calendar.DATE);
        } else if (field == Calendar.WEEK_OF_MONTH || field == Calendar.WEEK_OF_YEAR) {
            Calendar cal = Calendar.getInstance(Locale.CHINA);
            cal.setFirstDayOfWeek(Calendar.MONDAY);
            cal.setTime(DateUtils.truncate(date, Calendar.DATE));
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            return cal.getTime();
        } else if (field == Calendar.HOUR || field == Calendar.HOUR_OF_DAY) {
            return DateUtils.truncate(date, Calendar.HOUR);
        } else {
            throw new IllegalArgumentException("Un support date type : " + field);
        }
    }

    public static Date getEnding(Date date, int field) {

        if (field == Calendar.YEAR) {
            return DateUtils.addYears(DateUtils.truncate(date, Calendar.YEAR), 1);
        } else if (field == Calendar.MONTH) {
            return DateUtils.addMonths(DateUtils.truncate(date, Calendar.MONTH), 1);
        } else if (field == Calendar.DATE) {
            return DateUtils.addDays(DateUtils.truncate(date, Calendar.DATE), 1);
        } else if (field == Calendar.WEEK_OF_MONTH || field == Calendar.WEEK_OF_YEAR) {
            Calendar cal = Calendar.getInstance(Locale.CHINA);
            cal.setFirstDayOfWeek(Calendar.MONDAY);
            cal.setTime(DateUtils.truncate(date, Calendar.DATE));
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            return DateUtils.addWeeks(cal.getTime(), 1);
        } else if (field == Calendar.HOUR || field == Calendar.HOUR_OF_DAY) {
            return DateUtils.addHours(DateUtils.truncate(date, Calendar.HOUR), 1);
        } else {
            throw new IllegalArgumentException("Un support date type : " + field);
        }
    }

    /**
     * 比较两个日期的多个属性值是否相等
     *
     * @param left      左日期
     * @param right     右日期
     * @param fields    属性数组
     * @return          是否相等
     */
    public static boolean isEqual(Date left, Date right, int... fields) {
        Calendar cLeft = Calendar.getInstance();
        cLeft.setTime(left);
        Calendar cRight = Calendar.getInstance();
        cRight.setTime(right);
        for (int field : fields) {
            if (cLeft.get(field) != cRight.get(field)) {
                return false;
            }
        }
        return true;
    }

    /**
     * format the date in given pattern
     *
     * @param d date
     * @return the formatted string
     */
    public static String format(Date d) {
        if (d == null) {
            throw new IllegalArgumentException("given date is invalid ");
        }
        return format(d, Format.HYPHEN_YYYYMMDD);
    }

    /**
     * format the date in given pattern
     *
     * @param d       date
     * @param pattern time pattern
     * @return the formatted string
     */
    public static String format(Date d, String pattern) {
        if (null == d || StringUtils.isBlank(pattern)) {
            throw new IllegalArgumentException("given parameters is invalid " + d + " - " + pattern);
        }
        SimpleDateFormat formatter = (SimpleDateFormat) DateFormat.getDateInstance();
        formatter.applyPattern(pattern);
        return formatter.format(d);
    }

    /**
     * format the date in given pattern
     *
     * @param d       date
     * @param pattern time pattern
     * @return the formatted string
     */
    public static String format(Date d, Format pattern) {
        if (null == d || pattern == null) {
            throw new IllegalArgumentException("given date or pattern is invalid " + d + " - " + pattern);
        }
        SimpleDateFormat formatter = (SimpleDateFormat) DateFormat.getDateInstance();
        formatter.applyPattern(pattern.getFormat());
        return formatter.format(d);
    }

    public static String format1(Date d, Format pattern) {
        if (null == d || pattern == null) {
            return "";
        }
        SimpleDateFormat formatter = (SimpleDateFormat) DateFormat.getDateInstance();
        formatter.applyPattern(pattern.getFormat());
        return formatter.format(d);
    }

    /**
     * change string to date
     *
     * @param sDate the date string
     * @return Date object
     */
    public static Date format(String sDate) {
        Date dt = null;
        if (StringUtils.isNotBlank(sDate)) {
            for (Format format : Format.values()) {
                try {
                    dt = DateUtils.parseDateStrictly(sDate, format.getFormat());
                } catch (ParseException e) {
                    dt = null;
                    continue;
                }
                break;
            }
        }
        if (dt == null) {
            throw new IllegalArgumentException("given pattern is invalid " + sDate);
        }
        return dt;
    }

    /**
     * check format
     *
     * @param sDate     the date string to check
     * @param format    given format
     * @return          boolean
     */
    public static boolean formatCheck(String sDate, Format format) {
        try {
            DateUtils.parseDateStrictly(sDate, format.getFormat());
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * change string to date
     *
     * @param sDate the date string
     * @return Date object
     */
    public static Date format(String sDate, Format format) {
        Date dt = null;
        try {
            dt = DateUtils.parseDateStrictly(sDate, format.getFormat());
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        return dt;
    }

    /**
     * 将timestamp转换成date
     *
     * @param timestamp timestamp
     * @return Date
     */
    public static Date timestampToDate(Timestamp timestamp) {
        return new Date(timestamp.getTime());
    }

    /**
     * 将timestamp转换成date
     *
     * @param date
     * @return
     */
    public static Timestamp dateToTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }

    public static Date truncate(Date date, Format format) {
        return DateUtils.truncate(date, format.getType());
    }

    public static String getWeekOfDate(Date date) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

    public static String getWeekOfDate2(Date date) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

    /**
     * get months interval between two Date
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getMonths(Date date1, Date date2) {
        int iMonth = 0;
        try {
            Calendar objCalendarDate1 = Calendar.getInstance();
            objCalendarDate1.setTime(date1);
            Calendar objCalendarDate2 = Calendar.getInstance();
            objCalendarDate2.setTime(date2);
            if (objCalendarDate2.equals(objCalendarDate1)) {
                return 0;
            }
            if (objCalendarDate1.after(objCalendarDate2)) {
                Calendar temp = objCalendarDate1;
                objCalendarDate1 = objCalendarDate2;
                objCalendarDate2 = temp;
            }
            if (objCalendarDate2.get(Calendar.YEAR) > objCalendarDate1.get(Calendar.YEAR)) {
                iMonth = (objCalendarDate2.get(Calendar.YEAR) - objCalendarDate1.get(Calendar.YEAR)) * 12 + objCalendarDate2.get(Calendar.MONTH) - objCalendarDate1.get(Calendar.MONTH);
            } else {
                iMonth = objCalendarDate2.get(Calendar.MONTH) - objCalendarDate1.get(Calendar.MONTH);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iMonth;
    }

    /**
     * get date having some months offset the given date
     *
     * @param date      日期
     * @param months    增加或减少月份
     * @return          Date
     */
    public static Date getDiffMonth(Date date, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + months);
        return calendar.getTime();
    }

    /**
     * get date having some years offset the given date
     *
     * @param date  日期
     * @param years 年份
     * @return  结果
     */
    public static Date getDiffYear(Date date, int years) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + years);
        return calendar.getTime();
    }

    /**
     * get days which date1 is after date2
     *
     * @param date1 date1
     * @param date2 date2
     * @return      offset days
     */
    public static int getDays(Date date1, Date date2) {
        long iDay = Math.abs(date1.getTime() - date2.getTime()) / (1000 * 60 * 60 * 24);
        return (int) iDay;
    }

    /**
     * get date having some days offset the given date
     *
     * @param date the given date
     * @param days offset days
     * @return Date
     */
    public static Date getDiffDate(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + days);
        return calendar.getTime();
    }

    /**
     * get date before given date
     */
    public static Date getBeforeDate(Date date, int days) {
        return getDiffDate(date, -days);
    }

    /**
     * get date after given date
     */
    public static Date getAfterDate(Date date, int days) {
        return getDiffDate(date, days);
    }

    /**
     * get first month date
     */
    public static Date getMonthFirstDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * get last day of month of the given date
     */
    public static Date getMonthLastDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 先设置为下个月第一天
        // 加一个月
        calendar.add(Calendar.MONTH, 1);
        // 设置为该月第一天
        calendar.set(Calendar.DATE, 1);
        // 再减一天即为上个月最后一天
        calendar.add(Calendar.DATE, -1);
        // 设置时分秒
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * get days of given year and month
     */
    public static int getMonthDays(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        //把日期设置为当月第一天
        a.set(Calendar.DATE, 1);
        //日期回滚一天，也就是最后一天
        a.roll(Calendar.DATE, -1);
        return a.get(Calendar.DATE);
    }

    /**
     * get day of week with given date string and pattern
     */
    public static int getDayOfWeek(String dateStr, String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            Date date = sdf.parse(dateStr.substring(0, pattern.length()));
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(date.getTime());
            return cal.get(Calendar.DAY_OF_WEEK);
        } catch (ParseException e) {
            return -1;
        }
    }

    /**
     * get day of week with given date
     */
    public static int getDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * get day of week with current date
     */
    public static int getDayOfWeek() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }

    /**
     * get timestamp of tomorrow
     */
    public static Timestamp getNextDay() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return new Timestamp(c.getTimeInMillis());
    }

    /**
     * get date having some minutes offset the given date
     */
    public static String getDiffDayByMin(Integer min, Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, min);
        return format(c.getTime(), Format.HYPHEN_YYYYMMDDHHMMSS);
    }

    /**
     * 计算间隔天数，隔了夜就算一天，比如fromDate为2017-07-24 23:59:59, toDate为2017-07-25 00:00:00 间隔算一天
     *
     * @param fromDate  起始日期
     * @param toDate    终止日期
     * @return          间隔天数
     */
    public static long getIntervalDays(Date fromDate, Date toDate) {
        if (fromDate == null || toDate == null) {
            return 0;
        }
        LocalDate fromLocalDate = dateToLocalDate(fromDate);
        LocalDate toLocalDate = dateToLocalDate(toDate);
        return fromLocalDate.until(toLocalDate, ChronoUnit.DAYS);
    }

    /**
     * transform date to LocalDate
     * @param date  Date
     * @return LocalDate
     */
    public static LocalDate dateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime.toLocalDate();
    }

    /**
     * 获取当天的 23:59:59 的Date
     *
     * @return
     */
    public static Date getDateForLastTime(Date date) {

        String formatDate = format(date, Format.HYPHEN_YYYYMMDD);
        System.out.println(formatDate);
        LocalDateTime dateTime = LocalDateTime.parse(formatDate + " 23:59:59", DateTimeFormatter.ofPattern(Format.HYPHEN_YYYYMMDDHHMMSS.getFormat()));
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取当天的 00:00:01 的Date
     *
     * @return
     */
    public static Date getDateForFirstTime(Date date) {

        String formatDate = format(date, Format.HYPHEN_YYYYMMDD);
        LocalDateTime dateTime = LocalDateTime.parse(formatDate + " 00:00:01", DateTimeFormatter.ofPattern(Format.HYPHEN_YYYYMMDDHHMMSS.getFormat()));
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * check if date is after now
     *
     * @param date date
     * @return  boolean
     */
    public static boolean isAfterNow(Date date) {
        Date now = new Date();
        if (now.compareTo(date) > 0) {
            return false;
        }
        return true;
    }

    /**
     * parse date in given format
     *
     * @param source    date string
     * @param format    format
     * @return  Date
     */
    public static Date parseDate(String source, Format format) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format.getFormat());
            return simpleDateFormat.parse(source);
        } catch (Exception e) {
            log.warn("DateUtil parse date failed, source={}, formate={}", source, format.getFormat());
        }
        return null;
    }

    public static Date getDateTime(String time, Date date) {
        String dateToString = format(date, Format.HYPHEN_YYYYMMDD);
        String dateTimeString = dateToString + " " + time;
        return parseDate(dateTimeString, Format.HYPHEN_YYYYMMDDHHMMSS);
    }

    public static Date getCompareTime(Date date1, Date date2, boolean isAfter) {
        if (date1.before(date2)) {
            return isAfter ? date2 : date1;
        }
        return isAfter ? date1 : date2;
    }

    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowDate   当前时间
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return false 不在区间内
     * @author szh
     */
    public static boolean isEffectiveDate(String nowDate, String startDate, String endDate, Format f) {
        if (f == null) {
            return false;
        }
        try {
            String format = f.getFormat();
            Date startTime = new SimpleDateFormat(format).parse(startDate);
            Date endTime = new SimpleDateFormat(format).parse(endDate);
            Date nowTime = new SimpleDateFormat(format).parse(nowDate);
            long now = nowTime.getTime();
            long begin = startTime.getTime();
            long end = endTime.getTime();

            return now >= begin && now <= end;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * 获取某段时间内的所有日期
     */
    public static List<Date> findDates(Date dBegin, Date dEnd) {
        dBegin = truncate(dBegin, Format.HYPHEN_YYYYMMDD);
        dEnd = truncate(dEnd, Format.HYPHEN_YYYYMMDD);
        List lDate = new ArrayList();
        lDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        return lDate;
    }

    /**
     * 判断时间是否有重合
     *
     * @param list
     * @return
     */
    public static boolean checkOverlap(List<String> list) {
        //排序ASC
        Collections.sort(list);
        //是否重叠标识
        boolean flag = false;
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                //跳过第一个时间段不做判断
                String[] itime = list.get(i).split("_");
                for (int j = 0; j < list.size(); j++) {
                    //如果当前遍历的i开始时间小于j中某个时间段的结束时间那么则有重叠，反之没有重叠
                    //这里比较时需要排除i本身以及i之后的时间段，因为已经排序了所以只比较自己之前(不包括自己)的时间段
                    if (j == i || j > i) {
                        continue;
                    }

                    String[] jtime = list.get(j).split("_");
                    //此处DateUtils.compare为日期比较(返回负数date1小、返回0两数相等、返回正整数date1大)
                    long compare = diff(
                            (format(itime[0])),
                            (format(jtime[1])),
                            TimeUnit.SECONDS);
                    if (compare < 0) {
                        flag = true;
                        //只要存在一个重叠则可退出内循环
                        break;
                    }
                }
            }
            //当标识已经认为重叠了则可退出外循环
            if (flag) {
                break;
            }
        }
        return flag;
    }


    public static boolean date1IsAfterDate2(String date1, String date2, Format format) {
        Date d1 = parseDate(date1, format);
        Date d2 = parseDate(date2, format);
        return d1.after(d2);
    }

    /**
     * 判断该日期是否是该月的第一天
     *
     * @param date 需要判断的日期
     * @return 是否
     */
    public static boolean isFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH) == 1;
    }


    /**
     * 判断该日期是否是该月的最后一天
     *
     * @param date 需要判断的日期
     * @return 是否
     */
    public static boolean isLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH) == calendar
                .getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取上个月对应的日期
     *
     * @param currentDay     当前日期
     * @param supportDiffDay 是否支持日期不相等
     *                       如果支持正常返回
     *                       如果不支持返回null
     * @return
     */
    public static Date getPreviousMonthDay(Date currentDay, Boolean supportDiffDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDay);
        calendar.add(Calendar.MONTH, -1);
        Date previousDay = calendar.getTime();
        if (!supportDiffDay) {
            int currentDayInt = getDay(currentDay);
            int previousDayInt = getDay(previousDay);
            if (currentDayInt != previousDayInt) {
                return null;
            }
        }
        return previousDay;
    }

    /**
     * 月份与季度的映射
     */
    public final static Map<String, String> MON_2_QUARTER = new HashMap<String, String>() {{
        for (String month : MONTHS) {
            int mon = Integer.parseInt(month);
            int q = ((mon - 1) / 3) + 1;
            String quarter = "Q" + q;
            put(month, quarter);
        }
    }};

    /**
     * 根据月份获取季度
     *
     * @param month yyyy-MM
     * @return  yyyyQ1
     */
    public static String getQuarter(String month) {
        String[] split = month.split(Constants.DASH);
        return split[0] + MON_2_QUARTER.get(split[1]);
    }

    /**
     * 根据月份获取季度
     *
     * @param quarter yyyy-Qx
     * @return  1,2,3,4
     */
    public static Integer getQuarterIntValue(String quarter) {
        return quarter.charAt(quarter.length() - 1) - '0';
    }

    /**
     * 获取季度的第一个月份
     *
     * @param quarter
     * @return
     */
    public static String getFirstMonthOfQuarter(String quarter) {
        int qPosition = quarter.indexOf('Q') + 1;
        int q = quarter.charAt(qPosition) - '0';
        int firstMonthOfQuarter = q * 3 - 2;
        return String.format("%s-%02d", quarter.substring(0, qPosition - 1), firstMonthOfQuarter);
    }

    /**
     * 获取季度的最后一个月份
     *
     * @param quarter
     * @return
     */
    public static String getLastMonthOfQuarter(String quarter) {
        int qPosition = quarter.indexOf('Q') + 1;
        int q = quarter.charAt(qPosition) - '0';
        int lastMonthOfQuarter = q * 3;
        return String.format("%s-%02d", quarter.substring(0, qPosition - 1), lastMonthOfQuarter);
    }

    /**
     * 判断是否是季度
     *
     * @param quarter
     * @return
     */
    public static Boolean isQuarter(String quarter) {
        if (quarter.length() == QUARTER_DATE_LENGTH) {
            try {
                int index = quarter.indexOf("Q");
                if (index != -1) {
                    DateUtil.formatCheck(quarter.substring(0, index), Format.YYYY);
                    int q = quarter.charAt(quarter.length() - 1) - '0';
                    if (q >= 1 && q <= QUARTERS.length) {
                        return Boolean.TRUE;
                    }
                }
            } catch (Exception ignored) {
                // 校验失败
            }
        }
        return Boolean.FALSE;
    }

    public static final String QUARTER_FORMAT = "%04dQ%d";

    /**
     * 获取下一个季度
     *
     * @param quarter
     * @return
     */
    public static String getNextQuarter(String quarter) {
        if (isQuarter(quarter)) {
            int q = quarter.charAt(quarter.length() - 1) - '0';
            int year = Integer.parseInt(quarter.substring(0, quarter.indexOf('Q')));
            return q < 4 ? String.format(QUARTER_FORMAT, year, q + 1) : String.format(QUARTER_FORMAT, year + 1, 1);
        }
        return null;
    }

    /**
     * 获取上一个季度
     *
     * @param quarter
     * @return
     */
    public static String getPreviousQuarter(String quarter) {
        if (isQuarter(quarter)) {
            int q = quarter.charAt(quarter.length() - 1) - '0';
            int year = Integer.parseInt(quarter.substring(0, quarter.indexOf('Q')));
            return q > 1 ? String.format(QUARTER_FORMAT, year, q - 1) : String.format(QUARTER_FORMAT, year - 1, 4);
        }
        return null;
    }

    /**
     * 获取季度所在的年
     *
     * @param quarter   季度
     * @return  年
     */
    public static String getQuarterYear(String quarter) {
        if (isQuarter(quarter)) {
            return quarter.substring(0, 4);
        }
        return null;
    }

    /**
     * 查询距离diff的季度
     *
     * @param quarter
     * @param diff
     * @return
     */
    public static String getDiffQuarter(String quarter, int diff) {
        if (diff == 0) {
            return quarter;
        }
        int t = diff / 4;
        int year = Integer.parseInt(Objects.requireNonNull(getQuarterYear(quarter))) + t;
        quarter = year + quarter.substring(4);
        quarter = diff > 0 ? getPreviousQuarter(quarter) : getNextQuarter(quarter);
        for (int i = diff - t * 4; i != 0;) {
            if (diff > 0) {
                quarter = getNextQuarter(quarter);
                i--;
            }
            else {
                quarter = getPreviousQuarter(quarter);
                i++;
            }
        }
        return quarter;
    }
}
