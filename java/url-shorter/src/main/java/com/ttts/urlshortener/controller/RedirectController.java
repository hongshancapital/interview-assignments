package com.ttts.urlshortener.controller;

import com.ttts.urlshortener.base.exception.BusinessException;
import com.ttts.urlshortener.base.model.BaseResultCodeEnums;
import com.ttts.urlshortener.service.UrlQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/a")
@Api(value = "重定向")
public class RedirectController {

    private UrlQueryService urlQueryService;

    @Autowired
    public RedirectController(UrlQueryService urlQueryService) {
      this.urlQueryService = urlQueryService;
    }

    @ApiOperation(value = "短链接重定向")
    @GetMapping("/{surl}")
    public ResponseEntity<Void> redirect(@PathVariable("surl") String surl) {
        String result = urlQueryService.queryLurl(surl);
        if (result != null) {
            return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(result))
                .build();
        } else {
            log.debug("短链接无效:{}", surl);
            throw BusinessException.of(BaseResultCodeEnums.URL_INVALID);
        }
    }
}
