package com.cyh.assignment.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class Constants {
    /**
     * 设置最大容量，满后采用LRU更新
     */
    public static long MAX_CAPACITY;


    /**
     * 在最大容量固定的情况下，选择一个与==最大容量互质的质数==作为每次标号的增量
     * 可以保证在容量用完之前，小于maxCapacity的每一个标号都能利用到
     */
    public static long INCREMENT;

    /**
     * true : 内存充足
     * false : 内存使用率超过80%，暂停生成新的短域名
     */
    public static AtomicBoolean IS_MEMORY_ENOUGH = new AtomicBoolean(false);

    /**
     * 8位短码可以表示的最大ID
     */
    public static long MAX_ID = (long) (Math.pow(62,8) - 1);

    /**
     * 输入短域名，获取完整域名
     */
    public static final int ONLY_OPERATE_SHORT = 1;

    /**
     * 输入完整域名，检查是否存在短域名
     */
    public static final int ONLY_OPERATE_FULL = 2;

    /**
     * 新增长短域名映射关系
     */
    public static final int OPERATE_BOTH = 3;

    @Value("${maxCapacity}")
    public void setMaxCapacity(long maxCapacity) {
        MAX_CAPACITY = maxCapacity;
    }

    @Value("${increment}")
    public void setIncrement(long increment) {
        INCREMENT = increment;
    }
}
