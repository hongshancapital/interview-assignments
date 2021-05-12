package com.gaohf.shortener.service;

import com.gaohf.shortener.commons.response.ResponseResult;

public interface IUrlShortenerService {

    public ResponseResult create(String url);

    public ResponseResult getUrl(String id);

}
