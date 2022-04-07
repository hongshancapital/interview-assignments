package com.sequoia.service.impl;

import com.sequoia.service.ITinyUrlService;
import com.sequoia.service.ITinyUrlStore;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;

/**
 * TinyUrlGenerator
 *
 * @author KVLT
 * @date 2022-03-30.
 */
@Component
public class TinyUrlService implements ITinyUrlService {

    @Resource
    private ITinyUrlStore tinyUrlStore;


    @Override
    public CompletableFuture<String> getTinyUrl(String originUrl) {
        return tinyUrlStore.getTinyUrlFuture(originUrl);
    }

    @Override
    public CompletableFuture<String> getOriginUrl(String tinyCode) {
        return tinyUrlStore.getOriginUrlFuture(tinyCode);
    }

}
