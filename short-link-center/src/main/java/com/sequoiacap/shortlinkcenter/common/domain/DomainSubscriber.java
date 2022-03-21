/*
 *
 *  Copyright 2020 byai.com All right reserved. This software is the
 *  confidential and proprietary information of byai.com ("Confidential
 *  Information"). You shall not disclose such Confidential Information and shall
 *  use it only in accordance with the terms of the license agreement you entered
 *  into with byai.com.
 * /
 */

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
