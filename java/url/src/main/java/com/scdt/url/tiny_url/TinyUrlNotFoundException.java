package com.scdt.url.tiny_url;

import com.scdt.url.common.exception.AppException;
import com.scdt.url.tiny_url.model.TinyUrlId;

import java.util.Map;

import static com.scdt.url.common.exception.ErrorCode.TINY_URL_NOT_FOUND;

public class TinyUrlNotFoundException extends AppException {
    public TinyUrlNotFoundException(TinyUrlId id) {
        super(TINY_URL_NOT_FOUND, Map.of("tinyUrlId", id.toString()));
    }
}