package com.yofei.shortlink.controller;

import com.yofei.shortlink.dto.request.CreateShortLinkDto;
import com.yofei.shortlink.dto.response.CreateShortLinkResponse;
import com.yofei.shortlink.dto.response.QueryShortLinkResponse;
import com.yofei.shortlink.service.LinkService;
import com.yofei.shortlink.utils.FormatUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Api(tags = "short link")
@RestController
@RequestMapping("/link")
@Slf4j
public class LinkController {

    @Autowired
    private LinkService linkService;

    @PostMapping("/create")
    @ApiOperation("create short link")
    public CreateShortLinkResponse create(@ApiParam("原始网址") @RequestBody CreateShortLinkDto request,
                                          BindingResult bindingResult) {
        log.info("create short link request: {}", request);

        if (bindingResult.hasErrors()) {
            final String errorMsg = FormatUtil.format(bindingResult);

            log.warn("create short link failed: message={}", errorMsg);

            return CreateShortLinkResponse.fail(errorMsg);
        }



        return CreateShortLinkResponse.success(linkService.create(request.getLink()));
    }

    @ApiOperation("get source url")
    @GetMapping("/{code}")
    public QueryShortLinkResponse getUrl(@ApiParam("short code") @PathVariable(name = "code") String code) {
        String url = linkService.getUrl(code);

        if (url == null) {
            return QueryShortLinkResponse.fail("link is not existed");
        } else {
            return QueryShortLinkResponse.success(url);
        }
    }
}
