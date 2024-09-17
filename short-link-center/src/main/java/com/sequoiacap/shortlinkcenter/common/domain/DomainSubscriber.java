package com.sequoiacap.shortlinkcenter.common.domain;

/**
 * 领域事件订阅
 */
public interface DomainSubscriber<T extends DomainEvent> {

    /**
     * 事件处理方法
     *
     * @param t 领域事件
     */
    void doSubscribe(T t);
}
