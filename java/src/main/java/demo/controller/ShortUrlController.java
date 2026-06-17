package demo.controller;

import demo.common.R;
import demo.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: ShortUrlController
 * @Description: 长短链接转换api接口
 * @author Xia
 * @version V1.0
 * @Date 2021/12/15
 */
@RestController
@RequestMapping("/url")
@Api(value = "长短链接转换接口", tags = "长短链接转换接口", description = "长短链接转换接口")
public class ShortUrlController {

    @Autowired
    private ShortUrlService shortUrlService;

    @GetMapping("/getShortUrlByLong")
    @ApiImplicitParam(name = "longUrl", value = "长链接",dataType = "String",required = true)
    @ApiOperation(value = "根据长链获取短链", notes = "根据长链获取短链")
    public R getShortUrlByLong(String longUrl){
        if(longUrl == null){
            return R.error().message("缺少必填参数");
        }
        if(longUrl.isEmpty()){
            return R.error().message("参数为空");
        }
        return R.ok().data("shortUrl",shortUrlService.getShortUrl(longUrl));
    }

    @GetMapping("/getLongUrlByShort")
    @ApiImplicitParam(name = "shortUrl", value = "短链接",dataType = "String",required = true)
    @ApiOperation(value = "根据短链获取长链接", notes = "根据短链获取长链接")
    public R getLongUrlByShort(String shortUrl){
        if(shortUrl == null){
            return R.error().message("缺少必填参数");
        }
        if(shortUrl.isEmpty()){
            return R.error().message("参数为空");
        }
        return R.ok().data("longUrl",shortUrlService.getLongUrl(shortUrl));
    }
}
