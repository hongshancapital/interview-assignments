/*
 * Copyright (C) 2021 hongsan, Inc. All Rights Reserved.
 */
package com.example.shorturl.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

/**
 * index.html
 * com.example.shorturl.controller shorturl
 *
 * @create mencius 2021-02-18 19:31
 */

@RestController
public class ViewController {

    private static final Logger logger = LoggerFactory.getLogger(ViewController.class);

    /**
     * 首页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = {"/", "index", "index.html"})
    public Object index(HttpServletRequest request) {
        final Map<String, Object> params = WebUtils.getParametersStartingWith(request, "");
        for (Map.Entry<String, Object> item : params.entrySet()) {
            logger.info(String.valueOf(item));
        }
        if (CollectionUtils.isEmpty(params)) {
            return "hallo index.html";
        } else {
            return params;
        }
    }
}
