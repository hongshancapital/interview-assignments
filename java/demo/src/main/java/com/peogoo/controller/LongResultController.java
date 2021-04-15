package com.peogoo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/result")
public class LongResultController {

    /**
     * 长链接地址返回的对象
     */
    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public String result() {
        return "恭喜您，返回成功过了";
    }

}
