package com.interview.assignment.filter;

/**
 * 过滤器
 *
 * @param <T> 请求类型
 */
public interface Filter<T> {

  boolean filter(T request);

}
