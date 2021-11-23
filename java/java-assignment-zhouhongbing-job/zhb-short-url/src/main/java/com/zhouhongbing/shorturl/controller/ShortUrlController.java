package com.zhouhongbing.shorturl.controller;

import com.alibaba.fastjson.JSON;
import com.zhouhongbing.shorturl.dto.UrlBeanDto;
import com.zhouhongbing.shorturl.enm.AppHttpCodeEnum;
import com.zhouhongbing.shorturl.enm.IdworkerInstance;
import com.zhouhongbing.shorturl.entity.ShorterUrl;
import com.zhouhongbing.shorturl.utils.Base62Util;
import com.zhouhongbing.shorturl.utils.IdWorker;
import com.zhouhongbing.shorturl.utils.LRUCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Description: 发号器随机生成id后, 转化为短连接
 * <p>
 * 预计全球url总数为4294967296,按每个键值对0.2kb存储,需分配210G左右的内存.由于需要存储在jvm中,所以设置内存为2G
 */
@RestController
@RequestMapping("/shortUrl")
@Api(tags = "短域名管理", description = "短域名管理API")
public class ShortUrlController {

    private String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    // 短链url域名前缀
    @Value("${url.shorturl.prefix}")
    private String shortUrlPrefix;
    //缓存存储key-value键值对,预设值为2G,采用LRU算法

    //注入key-value缓存容器
    @Autowired
    private LRUCache map;


    private IdWorker idWorker;  //雪花算法生成器


    private Base62Util base62Util;// 62进制转换工具


    /**
     * 根据传入longUrl转化为端域名对象.
     *
     * @param longUrl
     * @return
     */
    @ApiOperation(value = "传入长域名,转换为短域名对象")
    @ApiImplicitParam(name = "longUrl", value = "域名请求对象", required = true, dataType = "UrlBeanDto")
    @PostMapping("url/getShortUrl")
    public ResponseEntity transfer2ShortUrl(@RequestBody UrlBeanDto longUrl) {
        //检查是否为空,为空则返回错误信息
        if (longUrl == null) {
            return ResponseEntity.status(AppHttpCodeEnum.PARAM_INVALID.getCode()).build();
        }
        //0.防范恶意攻击
        String longUrlStr = longUrl.getLongUrl();
        attackDefense(longUrlStr);

        //1.先在内存中查询关键字longUrl是否存在
        String jsonShortUrl = map.get(longUrl.getLongUrl());
        //1.1 如果存在则,直接返回value值,作为shortUrl
        //2.如果不存在以longUrl为key的值,则
        ShorterUrl shorterUrl = null;
        if (jsonShortUrl == null || StringUtils.isBlank(jsonShortUrl)) {
            //2.1 生成shortUrl对象
            shorterUrl = this.getShortUrlInfo(longUrl.getLongUrl());
            String s = JSON.toJSONString(shorterUrl);
            //2.2 然后以longUrl为key,以shortUrl为对象保存入缓存
            map.put(longUrl.getLongUrl(), s);
            //2.3 在存入已端域名为key,value为shortUrl为对象保存入缓存,便于做反向解析
            map.put(shorterUrl.getShorterUrl(), s);
        } else {
            //将jsonShortUrl转化为shortUrl对象
            shorterUrl = JSON.parseObject(jsonShortUrl, ShorterUrl.class);
        }


        //统计相关 todo
        statistics(longUrl);

        //3.将shortUrl放入responseEntity中
        return ResponseEntity.ok(shorterUrl);
    }


    /**
     * 根据短域名返回长域名
     *
     * @param shortUrl
     * @return
     */
    @ApiOperation(value = "传入短域名,转换为长域名地址返回")
    @ApiImplicitParam(name = "shortUrl", value = "域名请求对象", required = true, dataType = "UrlBeanDto")
    @PostMapping("url/getLongUrl")
    public ResponseEntity getLongUrl(@RequestBody UrlBeanDto shortUrl) {


        //检查是否为空,为空则返回错误信息
        if (shortUrl == null || StringUtils.isBlank(shortUrl.getShortUrl())) {
            return ResponseEntity.status(AppHttpCodeEnum.PARAM_INVALID.getCode()).build();
        }
        //如果传入的地址不是以规定的域名开头,则返回404,域名有问题

        if (!shortUrl.getShortUrl().startsWith(shortUrlPrefix)) {
            return ResponseEntity.status(AppHttpCodeEnum.PARAM_INVALID.getCode()).build();
        }


        String shortUrlBeanStr = map.get(shortUrl.getShortUrl());

        String longUrl = "";
        //如果shortUrlkey不为空或不为空字符串
        if (shortUrlBeanStr != null && !StringUtils.isBlank(shortUrlBeanStr)) {
            //取出对应的json字符串
            //将json字符串转化为shorterUrl对象
            ShorterUrl shorterUrl = JSON.parseObject(shortUrlBeanStr, ShorterUrl.class);
            //从shorterUrl对象取出longUrl字符串
            longUrl = shorterUrl.getLongUrl();

        }
        return ResponseEntity.status(HttpStatus.SEE_OTHER).body(longUrl);
    }


    private ShorterUrl getShortUrlInfo(String longUrl) {
        //创建shorterUrl对象
        ShorterUrl shorterUrl = new ShorterUrl();
        shorterUrl.setLongUrl(longUrl);
        //使用雪花算法生成id,并赋给shorterUrl对象
        long id = IdworkerInstance.INSTANCE.IdworkerInstance().nextId();
        shorterUrl.setId(id);
        //id由10进制转化为62进制的11位字符串

        String encode = Base62Util.encode(id, 11);
        shorterUrl.setBase62ShortUrl(encode);

        //截取低七位字符串为短域名,保存
        int length = encode.length();
        String shortUrlStr = shortUrlPrefix + encode.substring(length - 8, length);
        shorterUrl.setShorterUrl(shortUrlStr);

        //返回结果
        return shorterUrl;
    }

    /**
     * 可针对具体的攻击行为做相应的处理 todo
     *
     * @param longUrl
     */
    private void attackDefense(String longUrl) {

    }

    /**
     * 统计相关,可根据具体业务进行相关统计 todo
     *
     * @param longUrl
     */
    private void statistics(UrlBeanDto longUrl) {

    }
}
