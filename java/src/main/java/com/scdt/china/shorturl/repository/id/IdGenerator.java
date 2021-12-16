package com.scdt.china.shorturl.repository.id;

/**
 * ID生成器接口
 *
 * @author ng-life
 */
public interface IdGenerator {

    /**
     * 生成下一个ID
     *
     * @return ID
     */
    Long nextId();

    /**
     * 是否随机
     *
     * @return 是否
     */
    default boolean isRandom() {
        return false;
    }

}
