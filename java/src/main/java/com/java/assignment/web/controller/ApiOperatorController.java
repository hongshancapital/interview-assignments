package com.java.assignment.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.java.assignment.web.framework.CurrentLimit;
import com.java.assignment.web.utils.R;
import com.java.assignment.web.utils.ShortUrlUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Api(tags = "域名操作控制器")
@RestController
@RequestMapping("/url")
public class ApiOperatorController {

    private static Map<String,JSONObject> localUrlMap = new HashMap<>();
    /**
     * 短连接请求接受处理处
     *
     * @param shortUrlStr 短连接内容
     * @param response    response输出对象
     * @throws IOException 抛出的异常
     */
    @ApiOperation(value = "域名读取", notes = "接受短域名信息，返回长域名信息")
    @GetMapping(value = "/getLongUrl")
    public R getLongUrl(@ApiParam(name = "shortUrlStr", value = "短域名key", required = true) String shortUrlStr, HttpServletResponse response) {
        JSONObject obj = localUrlMap.get(ShortUrlUtils.SHORTURL + shortUrlStr);
        if (obj == null || StringUtils.isEmpty(obj.get("longUrl"))) {
            return new R().fail("链接不存在或已失效");
        }
        //重定向至长链接
//        response.sendRedirect(obj.get("longUrl").toString());
        return new R(obj.get("longUrl"));
    }

    // 此处设置的是1秒内运行访问10次
    @CurrentLimit(count = 10, timespan = 1)
    @ApiOperation(value = "域名存储", notes = "接受长域名信息，返回短域名信息")
    @GetMapping("/longToShort")
    public R longToShort(@ApiParam(name = "longUrl", value = "长域名", required = true) String longUrl) {
        String server = "http://localhost";
        String[] shortUrlArray = ShortUrlUtils.shortUrl(longUrl);
        String key = shortUrlArray[0];
        String shortUrl = String.format("%s/%s", server, key);
        JSONObject map = new JSONObject();
        // 缓存的短连接 对应的原始链接
        map.put("longUrl", longUrl);
        // 缓存的短连接的创建时间
        map.put("createTime", System.currentTimeMillis());
        // 缓存的短连接的字符串内容
        map.put("shortUrl", shortUrl);
        // 存储至内存
        localUrlMap.put(ShortUrlUtils.SHORTURL + shortUrl, map);
        return new R(map);
    }
}
