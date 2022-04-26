package com.scc.base.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author renyunyi
 * @date 2022/4/26 1:38 pm
 * @description short url compose hello page controller
 **/
@RestController
public class HelloController {

    @RequestMapping(path = "/shorturl-compose")
    public String hello(){
        return "This is shorturl-compose hello page!";
    }
}
