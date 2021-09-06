package com.sequcap.shorturl.service;

import com.sequcap.shorturl.web.model.UrlModel;

public interface UrlManagementService {

    public UrlModel buildUrlModel(String murmurCode, String longUrl);

    public UrlModel getUrlModel(String murmurCode);
}
