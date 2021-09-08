package com.moonciki.interview.controller.url;

import com.moonciki.interview.commons.base.BaseController;
import com.moonciki.interview.commons.enums.ResponseEnum;
import com.moonciki.interview.commons.exception.CustomException;
import com.moonciki.interview.commons.model.ResponseContent;
import com.moonciki.interview.service.url.ShortUrlService;
import com.moonciki.interview.vo.url.ShortUrlVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Api(tags = "001.短网址")
@Controller
public class ShortUrlController extends BaseController {

    @Autowired
    private ShortUrlService shortUrlService;

    @ApiOperation(value = "生成短网址")
    @ResponseBody
    @PostMapping("/shortUrl/createShort")
    public ResponseContent<ShortUrlVo> createShort(String fullUrl) {

        if(StringUtils.isBlank(fullUrl)){
            throw CustomException.createException(ResponseEnum.request_error.info("地址为空！"));
        }

        log.info("full_url = > " + fullUrl);

        ShortUrlVo shortUrl = shortUrlService.createShort(fullUrl);

        ResponseContent<ShortUrlVo> resp = ResponseContent.successResponse(shortUrl);

        return resp;
    }

    @ApiOperation(value = "获取短网址对应的真实地址")
    @ResponseBody
    @GetMapping("/shortUrl/getFullUrl")
    public ResponseContent<ShortUrlVo> getFullUrl(String shortUrl) {

        if(StringUtils.isBlank(shortUrl)){
            throw CustomException.createException(ResponseEnum.request_error.info("参数错误！"));
        }

        ShortUrlVo fullUrl = shortUrlService.getFullUrl(shortUrl);

        ResponseContent<ShortUrlVo> resp = ResponseContent.successResponse(fullUrl);

        return resp;
    }

    @ApiOperation(value = "302重定向到原始url")
    @GetMapping("/{shortUrl}")
    public void redirect(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {

        if(StringUtils.isBlank(shortUrl)){
            throw CustomException.createException(ResponseEnum.request_error.info("参数错误！"));
        }

        ShortUrlVo shortUrlVo = shortUrlService.getFullUrl(shortUrl);

        String fullUrl = null;
        if(shortUrlVo != null){
            fullUrl = shortUrlVo.getFullUrl();
        }

        if(StringUtils.isNotBlank(fullUrl)){
            response.sendRedirect(fullUrl);
        }else{
            String notfound = "/page/error/shortError.html";
            response.sendRedirect(notfound);
        }
    }

}
