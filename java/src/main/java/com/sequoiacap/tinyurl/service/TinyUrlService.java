package com.sequoiacap.tinyurl.service;

import com.sequoiacap.tinyurl.exception.BadParamException;
import com.sequoiacap.tinyurl.exception.DataNotExistException;

public interface TinyUrlService {
    String createTinyUrl(String url) throws BadParamException;

    String getUrl(String tinyUrl) throws BadParamException, DataNotExistException;
}
