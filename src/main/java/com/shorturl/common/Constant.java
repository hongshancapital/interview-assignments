package com.shorturl.common;

public class Constant {
    public static Integer MAX_LONG_URL_LENGTH = 10000;
    public static Integer MAX_SHORT_URL_LENGTH = 30;
    public static Integer MAX_CACHE_CAPACITY = 100000;
    public static Integer MAX_CACHE_EXPIRE_DAYS = 180;
    public static String SHORT_URL_PREFIX = "https://bit.ly/";

    public enum ValueType {
        SHORTURL, LONGURL
    }

}
