package com.wjup.shorturl.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.AopLog;
import com.wjup.shorturl.entity.UrlEntity;
import com.wjup.shorturl.service.UrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 短链接生成 demo1
 * @classname: UrlController
 * @author niuxing@huaxiapawn
 * @date 2021/3/21 14:55
*/
@Controller
@Api(tags = "短链接生成 demo1")
@Slf4j
@AopLog
public class UrlController {

    @Autowired
    private UrlService urlService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 创建短链接
     * 可支持多个长链接同时生成
     *
     * @param longUrl 原地址
     * @param viewPwd 访问密码
     * @param request 请求
     * @return json
     */
    @ApiOperation(value = "短链接生成", notes = "短链接生成")
    @ApiImplicitParams({
            @ApiImplicitParam(name="longUrl",value="长链接",required=true),
            @ApiImplicitParam(name="viewPwd",value="加密串",required=false,paramType="String")
    })
    @PostMapping("/create")
    @ResponseBody
    public String creatShortUrl(String longUrl, String viewPwd, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        String shortUrls = urlService.createShortUrl(longUrl, viewPwd, request);
        json.put("shortUrl", shortUrls);
        return json.toJSONString();
    }

    /**
     * 访问短链接
     *
     * @param shortUrlId 短网址id
     * @param response   响应
     * @param request    请求
     * @throws ServletException 异常捕获
     * @throws IOException      异常捕获
     */
    @GetMapping(value = "/{shortUrlId}")
    @ApiOperation(value = "短链接生成", notes = "短链接生成")
    @ApiImplicitParams({
            @ApiImplicitParam(name="shortUrlId",value="短域名访问",required=true),
    })
    @ResponseBody
    public void view(@PathVariable("shortUrlId") String shortUrlId, HttpServletResponse response, HttpServletRequest request) throws ServletException, IOException {

        UrlEntity urlEntity = urlService.findByShortUrlId(shortUrlId);
        if (urlEntity != null) {
            if (urlEntity.getViewPwd() != null && !"".equals(urlEntity.getViewPwd())) {
                request.setAttribute("shortUrlId", shortUrlId);
                request.getRequestDispatcher("/viewPwd").forward(request, response);
            } else {
                urlService.updateShortUrl(shortUrlId);
                response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
                response.setHeader("Location", urlEntity.getLongUrl());
            }
        } else {
            request.getRequestDispatcher("/noPage").forward(request, response);
        }
    }

    /**
     * 没有该请求跳转到指定页面
     *
     * @return page
     */
    @GetMapping("/noPage")
    public String noPage() {

        return "noPage";
    }

    /**
     * 有密码打开输入密码页面
     *
     * @return html
     */
    @PostMapping("/viewPwd")
    @ResponseBody
    public String viewPwd(HttpServletRequest request, Model model) {
        String shortUrlId = request.getAttribute("shortUrlId").toString();
        model.addAttribute("shortUrlId", shortUrlId);
        return "viewPwd";
    }

    /**
     * 验证密码是否正确
     *
     * @param viewPwd    密码
     * @param shortUrlId 短址id
     */
    @PostMapping("/VerifyPwd")
    @ResponseBody
    public String VerifyPwd(String viewPwd, String shortUrlId) {
        UrlEntity urlEntity = urlService.findByPwd(viewPwd, shortUrlId);

        JSONObject jsonObject = new JSONObject();
        if (urlEntity != null) {
            urlService.updateShortUrl(shortUrlId);
            jsonObject.put("longUrl", urlEntity.getLongUrl());
            jsonObject.put("flag", true);
        } else {
            jsonObject.put("flag", false);
        }
        return jsonObject.toJSONString();
    }

}
