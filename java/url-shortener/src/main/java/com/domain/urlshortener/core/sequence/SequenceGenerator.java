package com.domain.urlshortener.core.sequence;

/**
 * @author: rocky.hu
 * @date: 2022/4/5 1:44
 */
public interface SequenceGenerator {

    /**
     * 产生序列号
     *
     * @return
     *      序列号
     */
    Long generateSequenceNo();

}
