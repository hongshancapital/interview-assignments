package com.sequoia.infrastructure.common;

import lombok.AllArgsConstructor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Descript:
 * File: com.sequoia.infrastructure.common.ProducerPromise
 * Author: daishengkai
 * Date: 2022/3/30 21:20
 * Copyright (c) 2022,All Rights Reserved.
 */
@AllArgsConstructor
public class ProducerPromise<V> {

    final Thread producer;
    final CompletableFuture<V> promise = new CompletableFuture<>();
    final AtomicBoolean producing = new AtomicBoolean();

    public boolean shouldProduce() {
        return producer == Thread.currentThread() && !promise.isDone() && producing.compareAndSet(false, true) ;
    }

    public boolean complete(V value) {
        return promise.complete(value);
    }

    public boolean completeExceptionally(Throwable cause) {
        return promise.completeExceptionally(cause);
    }

    public CompletableFuture<V> promise() {
        return promise;
    }

}
