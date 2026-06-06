package com.jinblog.shorturl.common;


import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class UrlUtil {

    /**
     * 验证传入的要加密的字符串
     * @param url 链接
     * @return true为验证通过
     */
    public static boolean validateLongUrl(String url) {
        if (url == null) {
            return false;
        }
        if (url.length() < 9) {
            return false;
        }
        String scheme = url.substring(0, 8).toLowerCase();
        int protocolStartIndex = scheme.startsWith("http://") ? 7 : (scheme.startsWith("https://") ? 8 : -1) ;
        if (protocolStartIndex == -1) {
            return false;
        }
        return url.length() - protocolStartIndex >= 4;
    }

    /**
     * 获取内存使用情况
     * @return 返回一个大于0小于1的数，作为已使用内存的百分比
     */
    public static double usedMemoryPercent() {
        // 方式
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

        MemoryUsage memoryUsage = memoryMXBean.getHeapMemoryUsage(); //椎内存使用情况
        return (double) memoryUsage.getUsed() / memoryUsage.getMax();
    }
}
