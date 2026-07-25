package com.liu.shorturl.controller;

import com.liu.shorturl.dto.RestResponse;
import com.liu.shorturl.exception.BusinessException;
import com.liu.shorturl.service.ShortUrlServicce;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

/**
 * Description： 短域名服务接口
 * Date: Created in 2021/11/11 16:16
 * email: liujiao@fcbox.com
 * Version: 0.0.1
 * @author hello
 */
@RestController
@RequestMapping(value = "/shorturl")
@Api("短域名服务接口")
public class ShortUrlController {

    private Pattern pattern = Pattern.compile("((http://)|(https://)).+");

    @Autowired
    private ShortUrlServicce shortUrlServicce;

    /**
     * 根据短域名获取对应的长域名
     * @param surl 短域名
     * @return 对应的长域名
     */
    @GetMapping(value = "/getLongUrlByShorUrl")
    @ApiOperation(value="根据短域名获取对应的长域名", tags = "")
    public RestResponse<String> getLongUrlByShorUrl(@ApiParam(value = "短域名" ) @RequestParam String surl) {
        String urlByShortUrl = shortUrlServicce.getUrlByShortUrl(surl);
        return RestResponse.success(urlByShortUrl);
    }

    /**
     * 长域名转换为短域名
     * @param url 长域名
     * @return 对应的短域名
     */
    @PostMapping(value = "/addShortUrlByLongUrl")
    @ApiOperation(value="长域名转换为短域名", tags = "")
    public RestResponse<String> addShortUrlByLongUrl(@ApiParam(value = "长域名") @RequestParam String url) {
        if(! validateUrl(url)){
            throw new BusinessException("域名不合法");
        }
        String s = shortUrlServicce.addShortUrlByUrl(url);
        return RestResponse.success(s);
    }


    /**
     * 验证是否是合法域名
     * @param url
     * @return
     */
    private boolean validateUrl(String url) {
        if(StringUtils.isEmpty(url)) {
            return false;
        }
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            return false;
        }
        return pattern.matcher(url).matches();
    }
}
