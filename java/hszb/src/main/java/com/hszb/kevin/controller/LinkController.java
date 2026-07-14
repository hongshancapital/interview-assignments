package com.hszb.kevin.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.hszb.kevin.response.R;
import com.hszb.kevin.utils.GenerateStringUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.hszb.kevin.dto.LinkDto;

@RestController
@RequestMapping("link")
public class LinkController {

    public Map<String, LinkDto> linkMap = new HashMap<>();

    public final static String basic_short_string = "http://hszb.com/see/";

    Logger logger = LoggerFactory.getLogger(LinkController.class);

    @ApiOperationSupport(order = 1)
    @ApiOperation( value = "获取短链接接口")
    @GetMapping("short-link")
    public R<LinkDto> getShortLink(@RequestParam(value = "longLink") @ApiParam(name = "longLing", value = "长链接url", required = true) String longLink) {
        String shortLink = "";
        LinkDto linkDto = new LinkDto();
        linkDto.setLongLink(longLink);
        logger.info("入参：" + longLink);
        if(null == linkMap || !linkMap.containsKey(longLink)) {
            shortLink = basic_short_string+GenerateStringUtil.getLinkNo();
        }else {
            LinkDto link = linkMap.get(longLink);
            shortLink = link.getShortLink();
        }
        logger.info("返回值短链："+ shortLink);
        linkDto.setShortLink(shortLink);
        linkMap.put(longLink,linkDto);
        return R.ok(linkDto);
    }

    @ApiOperationSupport(order = 2)
    @ApiOperation( value = "获取长链接接口")
    @GetMapping("long-link")
    public R<LinkDto> getLongLink(@RequestParam(value = "shortLink") @ApiParam(name = "shortLink", value = "短链接url", required = true) String shortLink) {
        LinkDto result = new LinkDto();
        logger.info("入参：" + shortLink);
        result.setShortLink(shortLink);
        Iterator it = linkMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, LinkDto> entry = (Map.Entry) it.next();
            if(entry.getValue().getShortLink().equals(shortLink)) {
                result.setLongLink(entry.getKey());
                break;
            }
        }
        logger.info("返回值长链："+ result.getLongLink());
        if(StringUtils.isBlank(result.getLongLink())) {
            return R.fail("该短链接不存在");
        }else {
            return R.ok(result);
        }
    }

}
