package com.interview.assignment.generator;

/**
 * 通用的生成器接口
 *
 * @param <S> 参数
 * @param <T> 需要生成的目标值
 */
public interface Generator<S, T> {

  T generate(S request);
}
