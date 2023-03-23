package translation.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 时间帮助类
 *
 * @author: hello
 * @since: 2023/2/21
 */
public class DateUtil {
    private static final String date_format = "yyyy-MM-dd HH:mm:ss SSS";
    /**
     * 一切为了线程安全
     */
    private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>();
    private static ZoneId ZONE = ZoneId.of("Asia/Shanghai");

    public static DateFormat getDateFormat() {
        DateFormat df = threadLocal.get();
        if (df == null) {
            df = new SimpleDateFormat(date_format);
            threadLocal.set(df);
        }
        return df;
    }

    public static String formatDateToSSS(Date dt) throws ParseException {
        return getDateFormat().format(dt);
    }

    /**
     * 毫秒级别的
     */
    public static String formatDateToSSS(long dateLong) throws ParseException {
        return getDateFormat().format(new Date(dateLong));
    }

    public static long getCurrentDateTime() {
        return System.currentTimeMillis();
    }

    public static long betweenNow(long source, TimeUnit timeUnit) {
        Optional.ofNullable(source).orElseThrow(() -> new RuntimeException("source can not be null"));
        Optional.ofNullable(timeUnit).orElseThrow(() -> new RuntimeException("timeUnit can not be null"));
        return between(dateToLocalDateTimeConverter(new Date(source)), LocalDateTime.now(), timeUnit);
    }

    /**
     * 获取日期距离当前的差值
     *
     * @param source
     * @param timeUnit
     * @return
     */
    public static long betweenNow(Date source, TimeUnit timeUnit) {
        Optional.ofNullable(source).orElseThrow(() -> new RuntimeException("source can not be null"));
        Optional.ofNullable(timeUnit).orElseThrow(() -> new RuntimeException("timeUnit can not be null"));
        return between(dateToLocalDateTimeConverter(source), LocalDateTime.now(), timeUnit);
    }

    /**
     * 获取两个日期的差值
     *
     * @param source
     * @param target
     * @param timeUnit
     * @return
     */
    public static long between(LocalDateTime source, LocalDateTime target, TimeUnit timeUnit) {
        Optional.ofNullable(source).orElseThrow(() -> new RuntimeException("source can not be null"));
        Optional.ofNullable(target).orElseThrow(() -> new RuntimeException("target can not be null"));
        Optional.ofNullable(timeUnit).orElseThrow(() -> new RuntimeException("timeUnit can not be null"));
        return doBetween(source, target, timeUnit);
    }

    /**
     * 获取两个日期的差值
     *
     * @param source
     * @param target
     * @param timeUnit
     * @return
     */
    private static long doBetween(LocalDateTime source, LocalDateTime target, TimeUnit timeUnit) {
        Duration duration = Duration.between(source, target);
        switch (timeUnit) {
            case DAYS:
//                return Period.between(source.toLocalDate(), target.toLocalDate()).getDays();//又不是只看单位
                return duration.toDays();
            default:
                throw new RuntimeException("没有:" + timeUnit.name());
        }
    }

    /**
     * java.util.Date 转 LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime dateToLocalDateTimeConverter(Date date) {
        Optional.ofNullable(date).orElseThrow(() -> new RuntimeException("date can not be null"));
        return LocalDateTime.ofInstant(dateToInstantConverter(date), ZONE);
    }

    /**
     * java.util.Date 转 Instant
     *
     * @param date
     * @return
     */
    public static Instant dateToInstantConverter(Date date) {
        Optional.ofNullable(date).orElseThrow(() -> new RuntimeException("date can not be null"));
        return date.toInstant();
    }

//    public static void main(String[] args) throws ParseException {
//        Date dt = new Date(122,10, 10, 22, 16);
//        System.out.println("dt为:"+formatDateToSSS(dt));
//        System.out.println("测试看哈时间差距:"+betweenNow(dt,TimeUnit.DAYS));
//    }
}