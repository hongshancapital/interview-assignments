/**
 * Project: scdt-sdn
 * File created at 2022/3/13 21:18
 * Copyright (c) 2018 linklogis.com all rights reserved.
 */
package com.scdt.sdn.service;

/**
 * 功能描述
 *
 * @author donghang
 * @version 1.0
 * @type SdnService
 * @date 2022/3/13 21:18
 */
public interface SdnService {
    String encode(String url);

    String decode(String code);
}
