package ligq.url.controller;

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
@Validated
@Controller
public class UrlController {

    @Resource
    private UrlService service;
    @Value("${app.domain}")
    private String domain;

    /** 根据短url重定向到原始url地址 */
    @GetMapping("/{shortUrl}")
    public ResponseEntity<?> redirectUrl(@PathVariable("shortUrl") @NotBlank String shortUrl) throws Exception {
        String longUrl = service.getLongUrl(shortUrl);
        //设置请求头
        HttpHeaders headers = null;
        //设置响应状态
        HttpStatus statusCode;
        String retMsg = "";
        if (longUrl == null) {
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

    /** 使用原始长URL创建短URL */
    @PostMapping("/createShortUrl")
    public ResponseEntity<?> createUrl(@RequestParam("originUrl") @NotBlank @Length(max=2048) @URL String originUrl) throws Exception {
        String shortUrl = service.getShortUrl(originUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/plain");
        //设置响应状态
        HttpStatus statusCode = HttpStatus.OK;
        ResponseEntity<?> entity = new ResponseEntity(domain + "/" + shortUrl, headers, statusCode);
        return entity;
    }

    /** 获取缓存统计信息 */
    @ResponseBody
    @GetMapping("/cacheStats")
    public String createUrl() {
        return service.getCacheStats().toString();
    }
}
