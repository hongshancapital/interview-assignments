package com.wangxiao.shortlink.infrastructure.utils;

public class MachineIdUtils {

    public static String getMachineId(String shortLink) {
        return shortLink.substring(0, 2);
    }

    public static String getShortCode(String shortLink) {
        return shortLink.substring(2);
    }

    public static String combine(String machineId, String shortCode) {
        return machineId + shortCode;
    }
}
