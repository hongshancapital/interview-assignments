package com.alice.shortdomain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 首页跳转
 *
 * @author Alice [l1360737271@qq.com]
 * @date 2021/4/14 14:41
 */
@Controller
public class IndexController {

    @GetMapping(value = {"/", "index"})
    public String index() {
        return "index";
    }

}
