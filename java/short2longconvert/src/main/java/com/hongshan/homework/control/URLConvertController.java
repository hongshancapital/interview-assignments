package com.hongshan.homework.control;

import com.hongshan.homework.service.URLConvertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class URLConvertController {

    @Autowired
    private URLConvertService urlConvertService;

    @RequestMapping("/com/hongshan/homework/getShortURL")
    public String getShortURL(@RequestParam(name="longURL") String longURL){
        String shortURL = null;
         shortURL = urlConvertService.getShortURL(longURL);
         log.info("shortURL:"+shortURL);
        return shortURL;
    }

    @RequestMapping("/com/hongshan/homework/getLongURL")
    public String getLongURL(@RequestParam(name="shortURL") String shortURL){
        String longURL = null;
        longURL = urlConvertService.getLongURL(shortURL);
        log.info("longURL:"+longURL);
        return longURL;
    }
}
