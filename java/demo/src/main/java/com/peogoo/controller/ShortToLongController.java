package com.peogoo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @auth renxiaolong
 * @Date 2021-4-14 14:02:57
 * @Description
 */
@RestController
@RequestMapping(value = "/url")
public class ShortToLongController {
    private Logger logger = LoggerFactory.getLogger(ShortToLongController.class);

    private static final String SHORT_URL = "shortUrl:";

    @Autowired
    LongToShortController longToShortController;

    @GetMapping(value = "/{shortUrl}")
    public void getInvoiceUrl(@PathVariable(required = false) String shortUrl,
                              HttpServletResponse response) {
        String url = (String) longToShortController.urlMap.get(SHORT_URL + shortUrl);
        try {
            //将短链接映射的长链接取出并重定向到长链接
            response.sendRedirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}