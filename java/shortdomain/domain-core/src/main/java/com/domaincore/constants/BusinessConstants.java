package com.domaincore.constants;

/**
 * 业务常量
 *
 * @author Administrator
 * @Date 2021/9/21
 */
public class BusinessConstants {

    public static final String BASE_URL = "http://domainServer.com/";

    /**
     * 短域名最大长度
     */
    public static final int SHORTURL_MAX_LENGTH = 6;

    /**
     * map最大size限制
     */
    public static int MAXIMUM_CAPACITY = 1 << 32;

    /**
     * 雪花算法数据中心
     */
    public static int DATACENTERID = 3;

    /**
     * 雪花算法机器id/工作id
     */
    public static int WORKERID = 2;
}
