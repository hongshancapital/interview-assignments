package com.example.bean.mdc;

import org.slf4j.MDC;

import java.util.UUID;

/**
 * Description:
 *
 * @author: eric
 * @date: 2021/07/21
 */
public class MdcContext {
    public static final String TRACE_ID = "trace_id";
    public static final String EXTRA_BIZ_INFO = "extra_biz_info";

    public MdcContext() {
    }

    public static void setTraceId() {
        if (MDC.get("trace_id") == null) {
            MDC.put("trace_id", createTraceId() + "");
        }

    }

    public static void setTraceId(Object traceId) {
        if (traceId == null) {
            MDC.put("trace_id", createTraceId() + "");
        } else {
            MDC.put("trace_id", traceId.toString());
        }

    }

    public static String getTraceId(Object traceId) {
        String result;
        if (traceId == null) {
            result = createTraceId() + "";
            MDC.put("trace_id", result);
        } else {
            result = traceId.toString();
            MDC.put("trace_id", result);
        }

        return result;
    }

    public static String getTraceId() {
        return MDC.get("trace_id");
    }

    public static void put(String key, String val) {
        MDC.put(key, val);
    }

    public static String get(String key) {
        return MDC.get(key);
    }

    public static void remove(String key) {
        MDC.remove(key);
    }

    public static void clear() {
        MDC.clear();
    }

    private static long createTraceId() {
        long uuid = UUID.randomUUID().getMostSignificantBits();
        return uuid;
    }
}