package com.scdt.china.shorturl.storage;

import com.scdt.china.shorturl.pojo.Url;

import java.security.PrivateKey;

/**
 * @Author: zhouchao
 * @Date: 2021/12/08 17:36
 * @Description:
 */
public interface DataStorage {

    boolean saveUrl(String shortCode, Url url);

    Url mapping(String shortCode);
}
