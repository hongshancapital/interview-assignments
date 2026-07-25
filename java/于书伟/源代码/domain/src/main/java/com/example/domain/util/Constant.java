package com.example.domain.util;

/**
 * 常量
 */
public class Constant {

    public static final String[] CONSTANT_ARRAY_A_Z0_9 = new String[]
            { "a", "b", "c", "d", "e", "f","g", "h", "i", "j", "k", "l", "m",
                    "n", "o", "p", "q", "r", "s","t", "u", "v", "w", "x", "y",
                    "z", "0", "1", "2", "3", "4", "5","6", "7", "8", "9", "A",
                    "B", "C", "D", "E", "F", "G", "H", "I","J", "K", "L", "M",
                    "N", "O", "P", "Q", "R", "S", "T", "U", "V","W", "X", "Y",
                    "Z"};

    /** redis缓存空间 */
    public static final String CONTAINER_1 = "domainService-1";// 缓存1分钟
    public static final String CONTAINER_2 = "domainService-2";// 缓存120秒
    public static final String CONTAINER_LASTING = "domainService-lasting";// 持久缓存
}
