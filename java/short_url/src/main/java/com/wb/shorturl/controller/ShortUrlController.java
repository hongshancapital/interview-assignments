package com.wb.shorturl.controller;

import com.wb.shorturl.common.access.AccessLimit;
import com.wb.shorturl.service.ShortUrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author bing.wang
 * @date 2021/1/8
 */
@Controller
public class ShortUrlController {
    private static final Logger logger = LoggerFactory.getLogger(ShortUrlController.class);

    @Autowired
    private ShortUrlService shortUrlService;

    /**
     * 主页
     * return the index page
     */
    @AccessLimit()
    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String toIndex() {
        return "index";
    }

    /**
     * 登录页
     * return the login page
     */
    @AccessLimit(needLogin = false)
    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String tologin() {
        return "login";
    }

    /**
     * 跳转到对应的错误页面
     *
     * @param status the error status code
     * @return the page
     */
    @AccessLimit(needLogin = false)
    @RequestMapping(value = "/error/{status}", method = RequestMethod.GET)
    public String toErrorPage(@PathVariable int status) {
        return "error/" + status;
    }


    /**
     * 演示用的登录方法
     * @param username, password
     * @return 对应跳转结果
     */
    @AccessLimit(needLogin = false)
    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public String login(HttpServletRequest request, String username, String password){
        if(username.equals("test")&&password.equals("test123")){
            request.getSession().setAttribute("user",true);
            return "index";
        }
        return "login";
    }
    /**
     * 通过短码跳转到原网址
     *
     * @param shortCode the query shortCode
     * @return 原网址 or 500
     */
    @AccessLimit(needLogin = false)
    @RequestMapping(value = "/{shortCode:[a-zA-Z0-9]+}", method = RequestMethod.GET)
    public String shortCodeRedirect(@PathVariable String shortCode) {
        String originUrl = shortUrlService.getOriginUrlByShortCode(shortCode);
        if (originUrl == null) {
            logger.error("not found short Code:" + shortCode);
            return "redirect:error/500";
        }
        return "redirect:" + originUrl;
    }

}
