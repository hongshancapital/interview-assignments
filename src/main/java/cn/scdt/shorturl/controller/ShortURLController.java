package cn.scdt.shorturl.controller;


import cn.scdt.shorturl.exception.SystemErrorType;
import cn.scdt.shorturl.service.ShortURLService;
import cn.scdt.shorturl.utils.HashUtils;
import cn.scdt.shorturl.vo.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class ShortURLController {

    @Autowired
    private ShortURLService shortURLService;

    @GetMapping(value = {"/getFullURL"})
    @ApiOperation(value = "通过短地址得到长地址")
    @ApiImplicitParam(value = "需要复原的短地址", name = "shortURL", paramType = "query")
    public Result<String> getLongLink(String shortURL) {
        String fullURL = shortURLService.getFullURL(shortURL);
        return StringUtils.isBlank(fullURL) ?
                Result.fail(SystemErrorType.NOT_EXISTS_FULLURL.getMesg()) :
                Result.success(fullURL);
    }

    @GetMapping("/getShortURL")
    @ApiOperation(value = "长地址HASH得到短地址")
    @ApiImplicitParam(value = "需要缩短的长网址", name = "longURL", paramType = "query")
    public Result<String> getShortLink(String longURL) {
        String shortLink = shortURLService.getShortURL(longURL,HashUtils.hashToBase62(longURL));
        return Result.success(shortLink);
    }


}
