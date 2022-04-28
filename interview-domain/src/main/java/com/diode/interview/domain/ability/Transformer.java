package com.diode.interview.domain.ability;

/**
 * @author unlikeha@163.com
 * @date 2022/4/28
 */
public interface Transformer {
    String transform(String url);

    boolean suit(String type);
}
