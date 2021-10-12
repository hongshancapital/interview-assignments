package com.zy.url.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@Profile({"dev", "test"})
public class SwaggerController {

    @GetMapping("/")
    public String home() {
        return "redirect:swagger-ui.html";
    }
}
