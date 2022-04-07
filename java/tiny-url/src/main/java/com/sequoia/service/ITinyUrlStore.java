package com.sequoia.service;

import java.util.concurrent.CompletableFuture;

/**
 * Descript:
 * File: com.sequoia.service.ITinyUrlStore
 * Author: daishengkai
 * Date: 2022/3/30 19:34
 * Copyright (c) 2022,All Rights Reserved.
 */
public interface ITinyUrlStore {

    /**
     * 根据长链接获取对应的短链接信息
     * @param originUrl 长链接
     * @return
     */
    CompletableFuture<String> getTinyUrlFuture(String originUrl);

    CompletableFuture<String> getOriginUrlFuture(String tinyUrl);

}
