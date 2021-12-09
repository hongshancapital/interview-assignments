package com.scdt.url.tiny_url.model;

import com.scdt.url.common.ddd.Factory;
import org.springframework.stereotype.Component;

@Component
public class TinyUrlFactory implements Factory {

    //region variables
    private final TinyUrlIdGenerator idGenerator;
    public TinyUrlFactory(TinyUrlIdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }
    //endregion

    public TinyUrl create(String originalUrl) {
        TinyUrlId tinyUrlId = idGenerator.generate(originalUrl);
        return TinyUrl.create(tinyUrlId, originalUrl);
    }

}
