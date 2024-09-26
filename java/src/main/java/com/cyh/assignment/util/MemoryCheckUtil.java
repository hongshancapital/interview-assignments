package com.cyh.assignment.util;


import com.cyh.assignment.constant.Constants;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigDecimal;

public class MemoryCheckUtil {
    /**
     * 定时进行内存使用扫描，内存使用超过80%，停止生成新的短域名
     */
    @Scheduled(cron = "*/60 * * * * ?")
    public void execute() {
        long freeMemory = Runtime.getRuntime().freeMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        BigDecimal rate = new BigDecimal(freeMemory).divide(new BigDecimal(totalMemory),3,1);
        Constants.IS_MEMORY_ENOUGH.set(!(rate.doubleValue() < 0.2));
    }
}
