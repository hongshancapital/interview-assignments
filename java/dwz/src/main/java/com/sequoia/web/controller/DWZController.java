package com.sequoia.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sequoia.web.service.DWZService;
import com.sequoia.web.view.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class DWZController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DWZController.class);

    private static final String HTTP_PROTOCOL ="http://";

    @Autowired
    private DWZService service;

    ObjectMapper jsonMapper = new ObjectMapper();

    @PostMapping(value="/urlToShort",produces = "application/json;charset=UTF-8")
    @ApiOperation("接受长域名信息，返回短域名信息")
    @ResponseBody
    public Result urlToShort(@ApiParam(value = "需要生成短域名的长域名", required = true)
                                 @RequestParam String url,
                             @ApiParam(value = "URL编码，默认为UTF-8", required = false)
                                @RequestParam(required = false, defaultValue = "UTF-8") String encoding){
        if(!StringUtils.hasLength(url)){
            return Result.create(400,"url参数不可为空", url);
        }
        if(!url.contains("://"))
            url = HTTP_PROTOCOL + url;
        url = UriUtils.encodePath(url, encoding);
        String shortUrl = service.saveShortUrlByLongUrl(url);
        return Result.ok("生成短链接成功", shortUrl);
    }

    @RequestMapping(value = "/{shortUrl}", method = {RequestMethod.GET})
    @ApiOperation("接受短域名信息，跳转到对应长域名信息")
    public String redirectToLong(@ApiParam(value = "需要跳转的短域名", required = true)
                                     @PathVariable String shortUrl, HttpServletResponse response){
        String longURL = service.getLongUrlByShortUrl(shortUrl);
        if (longURL != null) {
            return "redirect:" + longURL;
        }
        // 这里改一下
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "application/json;charset=UTF-8");
        try {
            PrintWriter out = response.getWriter();
            String json = jsonMapper.writeValueAsString(Result.create(500,"不存在该链接", shortUrl));
            out.println(json);
        } catch (IOException e) {
            LOGGER.error("Error write response", e);
        }

        return null;

    }
}
