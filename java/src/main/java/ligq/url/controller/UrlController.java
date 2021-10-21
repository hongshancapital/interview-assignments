package ligq.url.controller;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.*;
import ligq.url.service.UrlService;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.net.URI;

/**
 * url请求controller
 * @author ligq
 * @since 2021-10-18
 */
@Api("短URL服务")
@Validated
@Controller
public class UrlController {

    @Resource
    private UrlService service;
    @Value("${app.domain}")
    private String domain;

    @ApiOperation("根据短url重定向到原始url地址")
    @ApiImplicitParam(name = "shortUrl", value = "短url地址", dataTypeClass = String.class, paramType = "path", required = true)
    @ApiResponses({
            @ApiResponse(code = 302, message = "请求成功将返回302重定向", responseHeaders = @ResponseHeader(name = "location", description = "重定向的原始URL")),
            @ApiResponse(code = 404, message = "未找到映射关系返回404"),
            @ApiResponse(code = 500, message = "服务器内部异常")
    })
    @GetMapping("/get/{shortUrl}")
    public ResponseEntity<?> redirectUrl(@PathVariable("shortUrl") @NotBlank String shortUrl) throws Exception {
        String longUrl = service.getOriginUrl(shortUrl);
        //设置请求头
        HttpHeaders headers = null;
        //设置响应状态
        HttpStatus statusCode;
        String retMsg = "";
        if (StrUtil.isEmpty(longUrl)) {
            statusCode = HttpStatus.NOT_FOUND;
            retMsg = "Cannot Found The LongUrl which mapping with " + shortUrl;
        } else {
            //设置请求头
            headers = new HttpHeaders();
            headers.setLocation(new URI(longUrl));
            statusCode = HttpStatus.FOUND;
        }
        ResponseEntity<?> entity = new ResponseEntity(retMsg, headers, statusCode);
        return entity;
    }

    @ApiOperation("使用原始长URL创建短URL")
    @ApiImplicitParam(name = "originUrl", value = "原始URL地址", dataTypeClass = String.class, paramType = "form", required = true)
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功将返回对应的短域名", response = String.class),
            @ApiResponse(code = 500, message = "参数校验错误、原始URL重复 或 服务器内部异常")
    })
    @PostMapping("/create")
    public ResponseEntity<?> createUrl(@RequestParam("originUrl") @NotBlank @Length(max=2048) @URL String originUrl) throws Exception {
        String shortUrl = service.getShortUrl(originUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/plain");
        //设置响应状态
        HttpStatus statusCode = HttpStatus.OK;
        ResponseEntity<?> entity = new ResponseEntity(domain + "/" + shortUrl, headers, statusCode);
        return entity;
    }

    @ResponseBody
    @ApiOperation("获取缓存统计信息")
    @ApiResponse(code = 200, message = "缓存统计信息", response = String.class)
    @GetMapping("/cacheStats")
    public String createUrl() {
        return service.getCacheStats().toString();
    }
}
