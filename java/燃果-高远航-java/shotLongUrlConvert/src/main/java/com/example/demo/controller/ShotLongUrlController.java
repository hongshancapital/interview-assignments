package com.example.demo.controller;

import com.example.demo.util.Md5Util;
import com.example.demo.util.RedisUtil;
import com.example.demo.util.UrlUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.common.Constants;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Api("实现短域名服务（细节可以百度/谷歌）")
@RestController
@RequestMapping("/api/convert")
public class ShotLongUrlController {

    @Resource
    private RedisUtil redisUtil;

    @GetMapping("/get-short-url")
    @ApiOperation("接受长域名信息，返回短域名信息")
    public String shortUrl(String url) {
        if(url.isEmpty()) return "传入地址不能为空！";
        String domain = UrlUtil.getDomain(url, 1);
        String[] split = url.split(domain + "/");
        String s = split[split.length-1];
        // 可以自定义生成 MD5 加密字符传前的混合 KEY
        String key = "www";
        // 要使用生成 URL 的字符
        String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
                "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
                "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z"
        };
        // 对传入网址进行 MD5 加密
        String hex = Md5Util.md5ByHex(key + s);
        // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
        String sTempSubString = hex.substring(Constants.INDEX_8, Constants.INDEX_8 + Constants.INDEX_8);
        // 这里需要使用 long 型来转换，因为 Integer.parseInt() 只能处理 31 位 , 首位为符号位 ,如果不用long，则会越界
        long lHexLong = Constants.LONG_0X3F & Long.parseLong(sTempSubString, Constants.INDEX_16);
        String outChars = "";
        for (int j = Constants.INDEX_0; j < Constants.INDEX_8; j++) {
            // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
            long index = Constants.LONG_0X0D & lHexLong;
            // 把取得的字符相加
            outChars += chars[(int) index];
            // 每次循环按位右移 5 位
            lHexLong = lHexLong >> Constants.INDEX_5;
        }
        String shortUrl = split[0] + domain + "/" +  outChars;

        //添加redis缓存
        Map<String, Object> urlMap = new HashMap<>();
        urlMap.put(shortUrl,url);
        boolean urlData = redisUtil.hmset("urlData", urlMap);
        return shortUrl;
    }

    @GetMapping("/get-long-url")
    @ApiOperation("接受短域名信息，返回长域名信息")
    public String getLongUrl(String url){
        if(url.isEmpty()) return "传入地址不能为空!";
        Map<Object, Object> urlData = redisUtil.hmget("urlData");
        String longUrl = urlData.get(url).toString();
        return longUrl;
    }



}
