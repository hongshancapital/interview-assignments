package com.duoji.shortlink.ability.generator;

/**
 * @author XY
 * @Description
 * @createTime 2021年12月18日 11:18:00
 */
public interface NumberGenerator {
    /**
     * 计数器ID
     * @return
     */
    Long ownId();

    /**
     * 产生的code
     * @return
     */
    Long generateCode();

}
