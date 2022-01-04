package com.jinblog.shorturl.controller;

import com.jinblog.shorturl.common.EventEnum;
import com.jinblog.shorturl.common.UrlUtil;
import com.jinblog.shorturl.config.ShortConfiguration;
import com.jinblog.shorturl.entry.Event;
import com.jinblog.shorturl.entry.Task;
import com.jinblog.shorturl.service.EventHandler;
import com.jinblog.shorturl.service.Generator;
import com.jinblog.shorturl.service.Storage;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;

@Api(value = "长链接转短链接")
@RestController
public class IndexController {
    @Autowired
    private Generator generator;
    @Autowired
    private Storage storage;

    @Autowired
    private EventHandler eventHandler;

    @Autowired
    @Qualifier("shortUrlEventHandlerPool")
    private ExecutorService shortUrlEventHandlerPool;

    @Autowired
    private ShortConfiguration shortConfiguration;

    @RequestMapping("/long-to-short")
    @ApiOperation(
            value = "为链接生产短链接",
            response = String.class,
            httpMethod = "GET",
            produces = "*/*",
            consumes = "text/html"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "生成的短链接", response = String.class),
            @ApiResponse(code = 403, message = "传入的链接格式错误或为空", response = String.class),
    })
    public ResponseEntity<String> longToShort(@RequestParam(name = "url", required = false) @ApiParam(value = "链接", required = true, example = "https://www.justexample.com/xxx.html") String url) {
        if (url != null) url = url.trim();
        if (!UrlUtil.validateLongUrl(url)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Wrong format url; example: https://jin.com");
        }
        // 检测内存是不是快满了
        if (UrlUtil.usedMemoryPercent() >= shortConfiguration.getHighestMemoryPercent()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("system is busy. please try again later");
        }
        String shortUrl = generator.generate();
        if (shortUrl == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("system is busy. please try again later");
        }
        storage.save(url, shortUrl);
        // 提交异步事件，用于触发回收策略
        shortUrlEventHandlerPool.submit(new Task(new Event(EventEnum.ADD_EVENT, shortUrl), eventHandler));
        return ResponseEntity.ok(shortConfiguration.getShortUrlDomain() + shortUrl);
    }

    @RequestMapping("/short-to-long")
    @ApiOperation(
            value = "获取短链接对应的原链接",
            response = String.class,
            httpMethod = "GET",
            produces = "*/*",
            consumes = "text/html"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "短链接对应的原链接", response = String.class),
            @ApiResponse(code = 403, message = "传入的短链接格式错误", response = String.class),
            @ApiResponse(code = 404, message = "无效短链接", response = String.class)
    })
    public ResponseEntity<String> shortToLong(@RequestParam(name = "url", required = false) @ApiParam(value = "短链接", required = true, example = "https://www.justexample.com/xxxxx") String url) {
        if (url != null) url = url.trim();
        if (!generator.validate(url)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Wrong format url;");
        }
        url = url.replace(shortConfiguration.getShortUrlDomain(), "");
        String longUrl = storage.find(url);
        if (longUrl == null || longUrl.length() < 1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        shortUrlEventHandlerPool.submit(new Task(new Event(EventEnum.GET_EVENT, url), eventHandler));
        return ResponseEntity.ok(longUrl);
    }
}
