package com.shorturl.controllers;

import com.shorturl.model.vo.ResponseMessage;
import com.shorturl.service.urlMappingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;
import java.io.IOException;

@RestController
@Validated
public class ShortUrlController {

    private static final Logger logger = LoggerFactory.getLogger(ShortUrlController.class);

    @Autowired
    private urlMappingService service;

    @RequestMapping(value = "/{base62:[0-9a-zA-Z]+$}", method = RequestMethod.GET)
    public String redirect(
            @PathVariable
                    String base62) throws IOException {
        String originUrl = null;
        try {
            originUrl = service.urlDecompress(base62);
        } catch (Exception e) {
            logger.info("no redirect target correspond with base62 {}, forward 404", base62);
            return "404 not found";
        }

        logger.info("base62 {} will be redirected {}", base62, originUrl);

        return originUrl;
    }

    @RequestMapping(value = "urlCompress")
    public ResponseMessage urlCompress(
            @RequestParam("url")
            @Size(min = 9, max = 300, message = "请求url格式有误,请重新输入")
                    String url) {

        String shortUrl = null;
        try {
            shortUrl = service.urlCompress(url);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("failed in compressing, unknown error");
            return new ResponseMessage(shortUrl, url, false, "url is not valid");
        }

        return new ResponseMessage(shortUrl, url, true, "succeed in compressing");
    }

    @RequestMapping(value = "decompress")
    public ResponseMessage decompress(@RequestParam("code") String code) throws IOException {

        String url = null;
        try {
            url = service.urlDecompress(code);
        } catch (Exception e) {
            String message = "failed in decompressing, no url correspond whth code " + code;
            logger.info(message);
            return new ResponseMessage(url, false, message, code);
        }

        logger.info("success in decompressing, url {} correspond whth code {}", url, code);
        return new ResponseMessage(url, true, "success in decompressing", code);
    }
}
