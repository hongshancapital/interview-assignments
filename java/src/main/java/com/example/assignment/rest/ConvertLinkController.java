package com.example.assignment.rest;

import com.example.assignment.common.LinkCommon;
import com.example.assignment.utils.ConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.google.common.hash.Hashing;

/**
 * @author Wang Ying
 * @date 2022.5.5
 * @description 长短域名转换控制类
 */
@RestController
@RequestMapping("/linkEnter")
@Api(tags = "域名处理相关接口")
public class ConvertLinkController {

    /**
     * 根据长域名信息，返回短域名信息
     *
     * @param url 原始链接
     * @return
     */
    @GetMapping("/convertToLongLink")
    @ApiOperation(value = "获取短域名")
    public String convertToLongLink(@RequestParam("url") String url) {
        //如果长域名已经生成过了，直接返回
        if(LinkCommon.longLinkMap.containsKey(url)){
            return LinkCommon.DOMAIN_PREFIX.concat("/a").concat("/").concat(LinkCommon.longLinkMap.get(url));
        }
        long num = Hashing.murmur3_32().hashUnencodedChars(url).padToLong();
        String shortLinkCode = ConvertUtil.base62Encode(num);
        LinkCommon.shortLinkMap.put(shortLinkCode, url);
        LinkCommon.longLinkMap.put(url, shortLinkCode);
        return LinkCommon.DOMAIN_PREFIX.concat("/a").concat("/").concat(shortLinkCode);
    }

    /**
     * 根据短域名信息，返回长域名信息
     *
     * @param shortUrl 短链接
     * @return
     */
    @GetMapping("/getLongLink")
    @ApiOperation(value = "获取长域名")
    public String getLongLink(@RequestParam("shortUrl") String shortUrl) {
        if(StringUtils.isNotBlank(shortUrl)){
            shortUrl = shortUrl.substring(shortUrl.lastIndexOf("/") + 1, shortUrl.length());
        }
        //如果短域名已经存在，直接返回
        if(LinkCommon.shortLinkMap.containsKey(shortUrl)){
            return LinkCommon.shortLinkMap.get(shortUrl);
        }
        return "no data found!";
    }
}