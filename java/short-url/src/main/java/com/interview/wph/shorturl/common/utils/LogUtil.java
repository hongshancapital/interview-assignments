package com.interview.wph.shorturl.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {

    /**
     * debug级别日志输出
     * @param clazz 类
     * @param msg 日志
     * @param params 其他参数
     */
    public static void debug(Class clazz, String msg, Object... params) {
        Logger logger = LoggerFactory.getLogger(clazz.getName());
        logger.debug(msg, params);
    }

    /**
     * trace日志输出
     * @param clazz 类
     * @param msg 日志
     * @param params 其他参数
     */
    public static void trace(Class clazz, String msg, Object... params) {
        Logger logger = LoggerFactory.getLogger(clazz.getName());
        logger.trace(msg, params);
    }

    /**
     * info级别日志输出
     * @param clazz 类
     * @param msg 日志
     * @param params 其他参数
     */
    public static void info(Class clazz, String msg, Object... params) {
        Logger logger = LoggerFactory.getLogger(clazz.getName());
        logger.info(msg, params);
    }

    /**
     * warn级别日志输出
     * @param clazz 类
     * @param msg 日志
     * @param params 其他参数
     */
    public static void warn(Class clazz, String msg, Object... params) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.warn(msg, params);
    }

    /**
     * error级别日志输出
     * @param clazz 类
     * @param msg 日志
     * @param params 其他参数
     */
    public static void error(Class clazz, String msg, Object... params) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.error(msg, params);
    }
}
