package com.asan.shorturlserver.controller;


import com.asan.shorturlserver.constants.Constants;
import com.asan.shorturlserver.dto.ResponseResult;
import com.asan.shorturlserver.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.validator.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/shortUrl/")
@Api(description = "短链接转换")
public class ShortUrlController {

    @Autowired
    ShortUrlService shortUrlService;

    @GetMapping("toShort")
    @ApiOperation(value="转为短链接",notes="转为短链接")
    public ResponseResult<String> toShort(@ApiParam("原始链接") @RequestParam String url) {

        UrlValidator defaultValidator = new UrlValidator();
        if (!defaultValidator.isValid(url)) {
            return ResponseResult.fail(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.name());
        }
        String shortUrl = Constants.MOCK_DOMAIN + shortUrlService.toShort(url);
        return ResponseResult.success(shortUrl);
    }

    @ApiOperation(value="短链接还原",notes="短链接还原")
    @GetMapping("/{code}")
    public ResponseResult<String> recovery(@ApiParam("短链接的CODE") @PathVariable(required = false) String code) {
        String url = shortUrlService.getOriginUrl(code);
        return ResponseResult.success(url);
    }
}
