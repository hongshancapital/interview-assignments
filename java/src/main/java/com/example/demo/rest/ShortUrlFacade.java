package com.example.demo.rest;

import com.example.demo.common.Result;
import com.example.demo.utils.LRUCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/service/shortUrlFacade")
@RestController
@Api(tags = "短链接工具接口")
public class ShortUrlFacade {

    private LRUCache lruCache;

    {
        lruCache = new LRUCache(3);
    }

    private String prefix = "http://t.cn/";

    // 要使用生成 URL 的字符
    char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
            'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'Z'};

    @ApiOperation("生成短链接")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "url", value = "原始链接", required = true)
    })
    @GetMapping("/generateShortUrl")
    public Result<String> generateShortUrl(@RequestParam(value = "url", required = true) String url) {
        if (!this.isUrl(url)) {
            return new Result<String>(100400, "URL格式错误", null);
        }
        //生成短URL
        String shortUrl = generateShortUuid(url);
        //设置缓存
        lruCache.setCache(shortUrl, url);
        //返回
        return new Result<String>(200, "OK", prefix + shortUrl);
    }

    @ApiOperation("得到原始链接")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shortUrl", value = "短链接", required = true)
    })
    @GetMapping("/getOriginalUrl")
    public Result<String> getOriginalUrl(@RequestParam(value = "shortUrl", required = true) String shortUrl) {
        //去除前缀
        shortUrl = shortUrl.replace(prefix, "");
        //从缓存获取
        Object value = lruCache.getCache(shortUrl);
        if (value != null) {
            return new Result<String>(200, "OK", value.toString());
        } else {
            return new Result<>(100401, "短链接不存在", null);
        }
    }


    /**
     * 生成短的UuId
     *
     * @param url
     * @return
     */
    private String generateShortUuid(String url) {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = DigestUtils.md5DigestAsHex(url.getBytes());
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(digits[x % 0x3E]);
        }
        return shortBuffer.toString();
    }

    /**
     * 验证是否是URL
     *
     * @param url
     * @return
     */
    public boolean isUrl(String url) {
        //转换为小写
        url = url.toLowerCase();
        String regex = "^((https|http|ftp|rtsp|mms)?://)"  //https、http、ftp、rtsp、mms
                + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@
                + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 例如：199.194.52.184
                + "|" // 允许IP和DOMAIN（域名）
                + "([0-9a-z_!~*'()-]+\\.)*" // 域名- www.
                + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." // 二级域名
                + "[a-z]{2,6})" // first level domain- .com or .museum
                + "(:[0-9]{1,5})?" // 端口号最大为65535,5位数
                + "((/?)|" // a slash isn't required if there is no file name
                + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
        return url.matches(regex);
    }
}
