package cn.sequoiacap.java.common.utils;

public class AutoIncreUtils {
    /** URL的编号（自增） */
    private static long urlId = 0;

    public static synchronized long getNextUrlId() {
        urlId++;
        return urlId;
    }
}
