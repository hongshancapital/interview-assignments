package com.moonciki.interview.commons.constant;

/**
 * 全局常量
 */
public class GlobalConstant {

    public static class Base{

        /** 包名 **/
        public final static String base_package = "com.moonciki.interview";

    }


    /**
     * redis key
     */
    public class RedisKey{
        /** 全局 redis key 前缀 **/
        public final static String base = "interview:";

        /** 系统缓存前缀 **/
        public final static String system_key = base + "system:";

        /** 系统字典表缓存 **/
        public final static String dic_config_map_key = system_key + "dicConfig:";

        /** 分布式事务锁前缀 **/
        public final static String distributed_Lock_key = system_key + "lock:";

        /** 短网址 **/
        public final static String short_url_md5 = base + "shortUrl:md5:";
        /** 短网址 **/
        public final static String short_url_alias = base + "shortUrl:alias:";

    }

    /** 分布式事务锁 key **/
    public static class DistributedLock{
        /** 测试 **/
        public final static String add_test = RedisKey.distributed_Lock_key + "addTest:";

    }

}
