/**
 * Project: scdt-sdn
 * File created at 2022/3/14 0:31
 * Copyright (c) 2018 linklogis.com all rights reserved.
 */
package com.scdt.sdn.exceptions;

/**
 * 功能描述
 *
 * @author donghang
 * @version 1.0
 * @type NotFoundException
 * @date 2022/3/14 0:31
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String msg) {
        super(msg);
    }
}
