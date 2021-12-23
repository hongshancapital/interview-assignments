package com.song.shorturl.web;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequestMapping("/shortUrl")
@Controller
public class ShortUrlController {

    private static int[] idxArray = new int[]{4, 9, 13, 18, 23, 28, 31};
    private static char[] lastCharArray = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
            'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static Map<String, String> shortToLongMap = new ConcurrentHashMap<>();

    @ApiOperation("获取短链接")
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/getShort")
    public String getShortUrl(String url) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(url.getBytes());
            // md5摘要算法的结果是长度为32的字符串
            String md5Result = new BigInteger(1, bytes).toString(16);

            StringBuilder builder = new StringBuilder();
            // 从字符串中取7个字符组成一个新的字符串
            for (int idx : idxArray) {
                builder.append(md5Result.charAt(idx));
            }
            String shortUrl = builder.toString();
            for (char lastChar : lastCharArray) {
                if (!shortToLongMap.keySet().contains(shortUrl + lastChar)) {
                    shortUrl = shortUrl + lastChar;
                    break;
                }
            }
            if (shortUrl.length() < 8) {
                shortUrl = shortUrl + "Z";
            }
            shortToLongMap.put(shortUrl, url);
            return "www.t.cn/" + shortUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ApiOperation("获取长链接")
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/getLong")
    public String getLongUrl(String shortUrl) {
        if (shortUrl.indexOf("/") < 0) {
            return null;
        }
        return shortToLongMap.get(shortUrl.split("/")[1]);
    }
}
