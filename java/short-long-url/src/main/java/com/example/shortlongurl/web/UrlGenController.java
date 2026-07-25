package com.example.shortlongurl.web;


import com.example.shortlongurl.framework.result.R;
import com.example.shortlongurl.web.model.ShortLongUrlModel;
import com.example.shortlongurl.web.service.IUrlGenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "URL生成接口")
@RestController
@RequestMapping("/genUrl")
public class UrlGenController {

    @Autowired
    private IUrlGenService IUrlGenService;

    @ApiOperation("生成短链接")
    @ApiImplicitParam(paramType = "query", name = "longUrl", value = "长链接", required = true, dataType = "String")
    @GetMapping("/long2short")
    public R<ShortLongUrlModel> getShortUrl(@RequestParam String longUrl){
        return R.ok(IUrlGenService.getShortUrl(longUrl));
    }

    @ApiOperation("获取长链接")
    @ApiImplicitParam(paramType = "query", name = "shortUrl", value = "短链接", required = true, dataType = "String")
    @GetMapping("/short2long")
    public R<ShortLongUrlModel> getLongUrl(@RequestParam String shortUrl){
        return R.ok(IUrlGenService.getLongUrl(shortUrl));
    }

}
