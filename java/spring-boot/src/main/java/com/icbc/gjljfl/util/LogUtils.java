package com.icbc.gjljfl.util;


import com.icbc.gjljfl.entity.NxHealthData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class LogUtils {
    private static final Logger log = LoggerFactory.getLogger(Object.class);
    private static Queue QUEUE = new LinkedList();
    private static Integer SIZE = 100;
    private static String MATTER_NAME = "spring-boot2系统";

    public static void info(String serviceName, String position, String name, String result) {
        save(serviceName, position, name, result);
        log.info("======>>>>>{}-{}-{}-{}-{}<<<<<=====", MATTER_NAME, serviceName, position, name, result);
    }

    public static void error(String serviceName, String position, String name, String result) {
        save(serviceName, position, name, result);
        log.error("======>>>>>{}-{}-{}-{}-{}<<<<<=====", MATTER_NAME, serviceName, position, name, result);
    }

    public static void save(String serviceName, String position, String name, String result) {

        NxHealthData data = new NxHealthData();
        data.setStartTime(com.icbc.gjljfl.util.DateUtils.dateToString());
        data.setMatternName(name);
        data.setPositon(position);
        data.setResult(result);
        data.setServiceName(serviceName);
        /*if (QUEUE.size() > SIZE) {
            QUEUE.clear();
        }
        QUEUE.add(data);*/
    }

    public static List selectQueue() {

        ArrayList lists = new ArrayList();
        if (QUEUE.size() > 0) {
            for (Object o : QUEUE) {
                lists.add(o.toString());
            }
        }
        return lists;
    }

    public static void clearQueue() {
        QUEUE.clear();
    }

    public static void main(String[] args) {
        int i = (int) Runtime.getRuntime().totalMemory() / 1024;//Java 虚拟机中的内存总量,以字节为单位
        System.out.println("总的内存量 i is " + getPrintSize(i));
        int j = (int) Runtime.getRuntime().freeMemory() / 1024;//Java 虚拟机中的空闲内存量
        System.out.println("空闲内存量 j is " + getPrintSize(j));
        System.out.println("最大内存量 is " + getPrintSize(Runtime.getRuntime().maxMemory() / 1024));
    }

    /**
     * 字节 转换为B MB GB
     *
     * @param size 字节大小
     * @return
     */
    public static String getPrintSize(long size) {
        long rest = 0;
        if (size < 1024) {
            return String.valueOf(size) + "B";
        } else {
            size /= 1024;
        }
        size = size / 1024;
        System.out.println("MB " + size);
        if (size < 1024) {
            return String.valueOf(size) + "KB";
        } else {
            rest = size % 1024;
            size /= 1024;
        }
        if (size < 1024) {
            size = size * 100;
            return String.valueOf((size / 100)) + "." + String.valueOf((rest * 100 / 1024 % 100)) + "MB";
        } else {
            size = size * 100 / 1024;
            return String.valueOf((size / 100)) + "." + String.valueOf((size % 100)) + "GB";
        }
    }
}
