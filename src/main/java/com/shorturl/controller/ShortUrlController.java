package com.shorturl.controller;

import com.shorturl.common.CodeMsg;
import com.shorturl.common.Constant;
import com.shorturl.domin.ShortUrlVo;
import com.shorturl.domin.response.WebResponse;
import com.shorturl.facade.ShortUrlFacade;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.shorturl.common.Constant.MAX_LONG_URL_LENGTH;
import static com.shorturl.common.Constant.MAX_SHORT_URL_LENGTH;
import static com.shorturl.common.Constant.ValueType.LONGURL;
import static com.shorturl.common.Constant.ValueType.SHORTURL;


@Controller
@RequestMapping("/")
public class ShortUrlController {

    private static Logger logger = LoggerFactory.getLogger(ShortUrlController.class);

    @Autowired
    private ShortUrlFacade shortUrlFacade;

    @RequestMapping("createShortUrl")
    @ResponseBody
    @ApiOperation(value = "短链接生成服务", notes = "根据长链接生成短链接", tags = "ShortUrl", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Url", value = "长链接", required = true, dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = WebResponse.class),
    })
    public WebResponse<ShortUrlVo> createShortUrl(@RequestParam(value = "Url") String url) {
        logger.info("短链接生成服务，长链接为->{}", url);
        if (!checkArgus(url, LONGURL)) {
            return WebResponse.error(CodeMsg.BIND_ERROR);
        }
        ShortUrlVo shortUrl = shortUrlFacade.createShortUrl(url);
        if (shortUrl == null) return WebResponse.error(CodeMsg.SERVER_ERROR);
        return WebResponse.success(shortUrl);
    }

    @RequestMapping("getLongUrl")
    @ResponseBody
    @ApiOperation(value = "短链接查询服务", notes = "根据短链接返回长链接", tags = "LongUrl", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "url", value = "短链接", required = true, dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = WebResponse.class),
    })
    public WebResponse<ShortUrlVo> getLongUrl(@RequestParam(value = "Url") String url) {
        logger.info("短链接查询服务，短链接为->{}", url);
        if (!checkArgus(url, SHORTURL)) {
            return WebResponse.error(CodeMsg.BIND_ERROR);
        }
        ShortUrlVo shortUrl = shortUrlFacade.getLongUrl(url);
        if (shortUrl == null) return WebResponse.error(CodeMsg.SERVER_ERROR);
        return WebResponse.success(shortUrl);
    }


    /**
     * 参数校验
     *
     * @param value
     * @param type
     * @return
     */
    private Boolean checkArgus(String value, Constant.ValueType type) {
        if (StringUtils.isEmpty(value)) {
            logger.error("String is null");
            return Boolean.FALSE;
        }
        if (type.equals(LONGURL) && value.length() > MAX_LONG_URL_LENGTH) {
            logger.error("Long Url is over max length,length->{}", value.length());
            return Boolean.FALSE;
        }
        if (type.equals(SHORTURL) && value.length() > MAX_SHORT_URL_LENGTH) {
            logger.error("Short Url is over max length,length->{}", value.length());
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
