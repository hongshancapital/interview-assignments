package com.liuwangpan.urlconvert.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.List;

/**
 * @Deacription jvm获取系统信息
 * @author wp_li
 **/
@Slf4j
public class RuntimeHelper {

    /**
     * 打印当前系统内存信息
     */
    public static void printCurrentSystemInfo() {
        MemoryMXBean memorymbean = ManagementFactory.getMemoryMXBean();
        log.info("堆内存信息: " + memorymbean.getHeapMemoryUsage());

        List<String> inputArgs = ManagementFactory.getRuntimeMXBean().getInputArguments();
        log.info("\n#####################运行时设置的JVM参数#######################");
        log.info(inputArgs.toString());

        log.info("\n#####################运行时内存情况#######################");
        long totle = Runtime.getRuntime().totalMemory();
        log.info("总的内存量 [" + totle + "]");
        long free = Runtime.getRuntime().freeMemory();
        log.info("空闲的内存量 [" + free + "]");
        long max = Runtime.getRuntime().maxMemory();
        log.info("最大的内存量 [" + max + "]");
    }
}