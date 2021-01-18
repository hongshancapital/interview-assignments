package com.wb.shorturl.controller;

import com.wb.shorturl.common.access.AccessLimit;
import com.wb.shorturl.common.exception.BaseErrorCode;
import com.wb.shorturl.common.web.ApiResponse;
import com.wb.shorturl.common.task.AsyncVo;
import com.wb.shorturl.common.task.TaskQueue;
import com.wb.shorturl.entity.ShortUrl;
import com.wb.shorturl.service.ShortUrlService;
import com.wb.shorturl.tools.Base62;
import com.wb.shorturl.tools.SnowFlake;
import com.wb.shorturl.tools.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author bing.wang
 * @date 2021/1/8
 */

@Controller
@RequestMapping(value = "api")
public class ShortUrlAPIController {

    @Value("${short-url.prefix}")
    private String prefix; //短网址前缀[http://localhost:8080]

    @Autowired
    private SnowFlake snowFlake;

    @Autowired
    private ShortUrlService shortUrlService;

    @Autowired
    private TaskQueue taskQueue;

    /**
     * 根据请求长网址返回短网址
     *
     * @param: originUrl
     * @return: the json response
     */
    @AccessLimit()
    @RequestMapping(value = "generateShortUrl", method = RequestMethod.POST)
    public @ResponseBody
    ApiResponse generateShortUrl(String originUrl) throws InterruptedException {

        if (originUrl.length() > 1379 || !Utils.isUrl(originUrl)) {
            return ApiResponse.prompt(BaseErrorCode.PARAMS_ERROR);
        }

        String shortCode = shortUrlService.getShortCodeByOriginUrl(originUrl);
        if (shortCode == null) {
            shortCode = Base62.fromBase10(snowFlake.nextId()); //获取一ID并将其转成62进制
            AsyncVo<ShortUrl, Object> vo = new AsyncVo<>();
            vo.setParams(new ShortUrl(shortCode, originUrl, System.currentTimeMillis()));
            taskQueue.getTaskQueue().put(vo); //把请求放入队列时进行异步处理
        }
        return ApiResponse.ok(prefix + shortCode);
    }

}
