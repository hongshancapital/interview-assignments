package com.duoji.shortlink.ability.model;

/**
 * @author XY
 * @Description
 * @createTime 2021年12月18日 16:19:00
 */
public class Counter {
    /**
     * 计数器id
     */
    protected Long id;

    /**
     * 计数器最大值
     */
    protected Long highMax;

    /**
     * 当前计数的低位
     */
    protected Long low;
    /**
     * 当前计数的高位
     */
    protected Long high;

    /**
     * 步长
     */
    protected Long step;
}
