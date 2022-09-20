package com.lisi.urlconverter.util;

/**
 * @description: 获取jvm信息工具类
 * @author: li si
 */
public class JVMUtil {
    /**
     * 获取jvm当前剩余内存
     * @return
     */
    public static long getFreeMemory(){
        return Runtime.getRuntime().freeMemory();
    }
}
