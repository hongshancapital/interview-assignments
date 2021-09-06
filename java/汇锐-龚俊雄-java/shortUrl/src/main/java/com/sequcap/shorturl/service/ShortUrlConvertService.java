package com.sequcap.shorturl.service;

import com.sequcap.shorturl.web.model.UrlModel;

public interface ShortUrlConvertService {

    public UrlModel long2Short(String longUrl);

    public UrlModel getLongUrlByShort(String shortUrl);

}
