package com.hongshan.shorturl.config;

/**
 * @author: huachengqiang
 * @date: 2022/1/16
 * @description:
 */
public interface Constants {
    /**
     * zookeeper获取workId的路径
     */
    String SUB_NODES_PATH_PREFIX = "/short-url/node-";
    /**
     * 步数
     */
    int STEPS = 16;
    /**
     * 步长
     */
    long GAP = 13600000000000L;
    /**
     * 偏移量
     */
    long OFFSET = 100000000L;
    /**
     * 短URL的前缀
     */
    String PREFIX = "http://xx.cn/";
}
