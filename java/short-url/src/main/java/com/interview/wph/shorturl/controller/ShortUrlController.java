package com.interview.wph.shorturl.controller;

import com.interview.wph.shorturl.common.consts.ConnConst;
import com.interview.wph.shorturl.common.utils.BloomUtil;
import com.interview.wph.shorturl.common.utils.EncryptionUtil;
import com.interview.wph.shorturl.common.utils.LogUtil;
import com.interview.wph.shorturl.dto.ResponseDto;
import com.interview.wph.shorturl.dto.web.ResultBuilder;
import com.interview.wph.shorturl.service.impl.ShortUrlServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/short-url")
@Api("短域名服务相关接口")
public class ShortUrlController {

    @Autowired
    private ShortUrlServiceImpl shortUrlService;
    @Value("${short.url.max_len:8}")
    private Integer shortUrlMaxLen;

    @PostMapping("/")
    @ApiOperation("添加长域名,返回短域名")
    public ResponseDto postLongUrl(String longUrl) {
        if (!StringUtils.hasLength(longUrl)) {
            LogUtil.error(this.getClass(), "长域名{}不合法,长度需大于0", longUrl);
            return ResultBuilder.fail(ConnConst.ILLEGAL_PARAM, ConnConst.ILL_LONG_URL);
        }
        //返回
        return ResultBuilder.success(ConnConst.CREATED_CODE, shortUrlService.postLongUrl(longUrl));
    }

    @GetMapping("/")
    @ApiOperation("输入短域名,返回长域名")
    public ResponseDto getLongUrl(String shortUrl) {
        if (!StringUtils.hasLength(shortUrl) || shortUrl.length() > shortUrlMaxLen) {
            LogUtil.error(this.getClass(), "短域名{}不合法,长度范围[1-8]", shortUrl);
            return ResultBuilder.fail(ConnConst.ILLEGAL_PARAM, ConnConst.ILL_LEN_SHORT_URL);
        }
        Long shortId = EncryptionUtil.decimalConvertToNumber(shortUrl);
        String ret = null;
        if (!BloomUtil.containsId(shortId) || (ret = shortUrlService.getLongUrl(shortId)) == null) {
            LogUtil.error(this.getClass(), "短域名{}不存在", shortUrl);
            return ResultBuilder.fail(ConnConst.NOT_FOUND, ConnConst.NOT_FOUND_MSG);
        }
        return ResultBuilder.success(ret);
    }
}
