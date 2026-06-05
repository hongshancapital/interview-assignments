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
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


@Slf4j
@Api(tags = "域名操作控制器")
@RestController
@RequestMapping("/url")
public class ApiOperatorController {

    //此处静态对象来方便做测试，正常情况是需要写数据库和Redis
    private static Map<String, JSONObject> localUrlMap = new HashMap<>();

    //线程索，防止并发击穿缓存
    private ReentrantLock lock = new ReentrantLock();

    //线程索，防止并发击穿缓存，使用读写锁
    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    // 此处设置的是1秒内运行访问10次
    @CurrentLimit(count = 10, timespan = 1)
    @ApiOperation(value = "域名存储", notes = "接受长域名信息，返回短域名信息")
    @GetMapping("/longToShort")
    public R longToShort(@ApiParam(name = "longUrl", value = "长域名", required = true) String longUrl) {
        String server = "http://localhost";
        String key = ShortUrlUtils.shortUrl(longUrl);
        String shortUrl = String.format("%s/%s", server, key);
        String cacheKey = ShortUrlUtils.SHORTURL + shortUrl;
        // 为防止 缓存穿透、击穿导致多次操作内存，因场景不复杂，先简单的写成如下内容
        //读之前先上读锁
        readWriteLock.readLock().lock();
        //从内存(条件有限，此处应用redis)中查找是否已经存在有该短连接
        JSONObject cacheObj = localUrlMap.get(cacheKey);
        //没有数据，需要写，读锁释放
        readWriteLock.readLock().unlock();
        //如果有，则说明已经为该长链接生成过
        if (cacheObj != null && cacheObj.get("shortUrl") != null) {
            return new R(cacheObj);
        }
        try {
            // 高并发下需要双重检查，如果还是空说明真的没有缓存
            //加写锁，必须先释放读锁，不然这里直接GG
            readWriteLock.writeLock().lock();
            try {
                cacheObj = new JSONObject();
                // 缓存的短连接 对应的原始链接
                cacheObj.put("longUrl", longUrl);
                // 缓存的短连接的创建时间
                cacheObj.put("createTime", System.currentTimeMillis());
                // 缓存的短连接的字符串内容
                cacheObj.put("shortUrl", shortUrl);
                // 存储至内存()
                //TODO 正常情况 数据库和redis都要写一份
                localUrlMap.put(cacheKey, cacheObj);
            } finally {
                //这里要先降级，给自己占一个坑，防止当前线程释放写锁之后，马上有其他线程获取到写锁，并对对象修改
                readWriteLock.readLock().lock();
                cacheObj = localUrlMap.get(cacheKey);
                //finally里面释放写锁
                readWriteLock.writeLock().unlock();
            }
        } finally {
            //finally里面释放读锁
            readWriteLock.readLock().unlock();
        }
        return new R(cacheObj);
    }


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

}
