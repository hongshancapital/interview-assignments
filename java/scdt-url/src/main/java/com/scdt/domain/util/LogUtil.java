package com.scdt.domain.util;

import org.slf4j.Logger;

/**
 * 日志统一输出工具
 * 张来刚
 * 2021/10/9 0009.
 */
public class LogUtil {

    public static void info(Logger logger, String info, Object... param) {
        if (logger.isInfoEnabled()) {
            logger.info(info, param);
        }
    }

    public static void debug(Logger logger, String info, Object... param) {
        if (logger.isDebugEnabled()) {
            logger.debug(info, param);
        }
    }

    public static void warn(Logger logger, String info, Object... param) {
        if (logger.isWarnEnabled()) {
            logger.warn(info, param);
        }
    }

    public static void error(Logger logger, String info, Object... param) {
        if (logger.isErrorEnabled()) {
            logger.error(info, param);
        }
    }
}
