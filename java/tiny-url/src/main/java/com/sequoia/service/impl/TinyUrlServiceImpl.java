package com.sequoia.service.impl;

import com.sequoia.service.ITinyUrlService;
import com.sequoia.service.ITinyUrlStore;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;

/**
 * TinyUrlService
 *
 * @author KVLT
 * @date 2022-03-30.
 */
@Component("tinyUrlService")
public class TinyUrlServiceImpl implements ITinyUrlService {

    @Resource
    private ITinyUrlStore tinyUrlStore;

    @Override
    public CompletableFuture<String> getTinyUrlFuture(String originUrl) {
        return tinyUrlStore.getTinyUrlFuture(originUrl);
    }

    @Override
    public String getOriginUrl(String tinyCode) {
        return tinyUrlStore.getOriginUrl(tinyCode);
    }

}
