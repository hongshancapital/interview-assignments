package com.test.shorturl.controller;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 1、基本实现逻辑
 a.要求映射数据存储在JVM内存即可,因此使用HashMap存储，key为短域名，value为长域名，满足快速取值需求
   如果要求并发安全，也可以使用CurrentHashMap存储；
   对于高并发要求，可以将数据持久化存储在mysql数据库，然后将近1个月的数据存储在redis，保证存取速度；
 b.实现过程只需要为长域名生成一个唯一id，然后将唯一id转换为短域名url，存储短域名与长域名的映射关系即可

 2、唯一id生成逻辑
 a.最简单的方式使用一个自增变量作为唯一id，比如使用redis或mysql或放号器原理实现自增id
 b.分布式系统也可以使用雪花算法生成唯一id
 为了简化程序，此处直接用变量保存唯一id

 3、唯一id转换为短域名逻辑
 url中只有a-z、A-Z、0-9共62个字符不需要编码，为了使短域名尽可能短，因此使用这62个字符生成短域名；
 为了防止遍历短域名，将a-z、A-Z、0-9进行乱序；

 为了过滤无效的请求，使用最后一个字符作为校验码，即对短域名计算sum，将前面生成的短域名字符的整形值相加然后对62取余，然后经过base62编码得到校验码；
 这样7位字符生成短域名url，1位字符作为校验码，短域名容量约3.5万亿，足够使用，1～3位的短域名均保留，可以留作它用。

 */
@RestController
@Api(tags = "短域名")
public class ShortURLController {
    //初始值设置为238328，保证短域名有至少4位字符
    private static Integer UniqueID = 238328;
    private static Map<String,String> URLMap = new HashMap<String,String>();

    private static Integer CharaterLenth = 62;
    private final static String Charaters = "1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";

    @GetMapping("/GetLongURL")
    @ApiOperation(value = "根据短域名获取对应的长域名" , notes = "Not Found表示未找到", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="shortURL",value="短域名url",required = true)
    })
    public String GetLongURL(@RequestParam String shortURL){
        if (shortURL.length()<=0){
            return "Not Found";
        }
        //先计算sum 验证短域名url是否正确
        String[] urls = shortURL.split("\\?");
        String baseURL= urls[0];
        urls = baseURL.split("/");
        String realShortURL =urls[urls.length-1];
        String sum = GetSumOfShortURL(realShortURL.substring(0,realShortURL.length()-1));

        if (!sum.equals(realShortURL.substring(realShortURL.length()-1))){
            return "Not Found";
        }

        //查找map映射并返回
        if (URLMap.containsKey(realShortURL)){
            return URLMap.get(realShortURL);
        }
        return "Not Found";
    }

    @PostMapping ("/SetLongURL")
    @ApiOperation(value = "根据长域名设置短域名" , notes = "success表示设置成功，failed表示设置失败", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="longRUL",value="长域名url",required = true)
    })
    public String SetLongURL(@RequestParam String longRUL){
        if (longRUL.length()<=0){
            return "invalid url";
        }
        String baseURL = DecodeIDInBase62(UniqueID++);
        String shortURL = baseURL + GetSumOfShortURL(baseURL);
        URLMap.put(shortURL,longRUL) ;
        return shortURL;
    }

    //将id转换为短域名url
    public static String DecodeIDInBase62(Integer uniqueID){
        if (uniqueID <= 0) {
            return "";
        }
        StringBuffer buffer = new StringBuffer();
        while (uniqueID>0){
            Integer round = uniqueID/CharaterLenth;
            Integer remain = uniqueID % CharaterLenth;
            buffer.append(Charaters.toCharArray()[remain]);
            uniqueID=round;
        }
        return buffer.toString();
    }

    //根据shortURL计算sum sum转换为base62编码值
    public static String GetSumOfShortURL(String shortURL){
        if (shortURL.length()<=0){
            return "";
        }
        Integer sum = 0;
        char[] urlCharArray = shortURL.toCharArray();
        for(int i=0;i<shortURL.length();i++){
            sum += urlCharArray[i]-'0';
        }
        Integer remain = sum % CharaterLenth;
        return String.valueOf(Charaters.toCharArray()[remain]);
    }

}
