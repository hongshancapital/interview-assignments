package com.demo.service;

import java.util.Map;

/**
 * Created by gouyunfei on 2021/4/14.
 */
public interface URLService {

    String getUrl(String shortUrl);

    String setUrl(String longUrl);

    Map<String, Object> getContext();
}
