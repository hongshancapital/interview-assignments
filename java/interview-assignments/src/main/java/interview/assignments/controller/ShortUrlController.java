package interview.assignments.controller;

import interview.assignments.service.IShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhiran.wang
 * @date 2022/4/10 23:14
 */
@Api(value = "短链接接口")
@RequestMapping("/shortUrl")
@RestController
public class ShortUrlController {

    @Autowired
    private IShortUrlService shortUrlService;

    @ApiOperation(value = "短域名存储接口")
    @GetMapping("/saveLongUrl")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "longUrl", value = "长域名信息", paramType = "query")
    })
    public String saveLongUrl(String longUrl) {
        String shortUrl = shortUrlService.getShortUrl(longUrl);
        return shortUrl;
    }

    @ApiOperation(value = "短域名读取接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shortUrl", value = "短域名信息", paramType = "query")
    })
    @GetMapping("/queryLongUrl")
    public String queryLongUrl(String shortUrl) {
        String longUrl = shortUrlService.getLongUrl(shortUrl);
        return longUrl;
    }
}
