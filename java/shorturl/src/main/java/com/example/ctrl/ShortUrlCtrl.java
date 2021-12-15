package com.example.ctrl;

import com.example.serv.IShortUrlServ;
import com.example.utils.CMyKey;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/url")
@Api(value = "短域名", tags = "短域名接口")
public class ShortUrlCtrl {
    private final static Map<CMyKey, String> map = new HashMap<>();
    private IShortUrlServ shortUrlServ;

    public ShortUrlCtrl(IShortUrlServ shortUrlServ) {
        this.shortUrlServ = shortUrlServ;
    }

    /**
     * 短域名存储接口
     * @param longUrl 长域名信息
     * @return 短域名信息
     */
    @PostMapping("/transLong")
    @ApiOperation(value = "返回短域名", notes = "接收长域名信息并储存返回解析后对应的短域名信息")
    @ApiOperationSupport(order = 1)
    public String transLong(@Valid @RequestParam String longUrl) {
        String shortUrl = this.shortUrlServ.transLong(longUrl);
        this.map.put(new CMyKey(shortUrl), longUrl);

        return shortUrl;
    }

    /**
     * 短域名读取接口
     * @param shortUrl 短域名信息
     * @return 长域名信息
     */
    @GetMapping("/transShort")
    @ApiOperation(value = "返回长域名", notes = "接收短域名信息返回解析后对应的长域名信息无存储返回null")
    @ApiOperationSupport(order = 2)
    public Map transShort(@Valid @RequestParam String shortUrl) {
        Map rMap = new HashMap();
        String longUrl = this.map.get(new CMyKey(shortUrl));
        String code = null != longUrl ?  "1" : "0";
        rMap.put("code", code);
        rMap.put("param", longUrl);

        return rMap;
    }

}

