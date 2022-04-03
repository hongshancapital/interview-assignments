package com.zhucan.domain.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhuCan
 * @description
 * @since 2022/4/3 15:57
 */
@Controller
@RequestMapping
public class IndexController {

  @GetMapping
  public String apiDocs(){
    return "redirect:/swagger-ui.html";
  }
}
