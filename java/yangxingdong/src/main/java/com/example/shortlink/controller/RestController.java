package com.example.shortlink.controller;


import com.example.shortlink.service.ShortLongLinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.web.bind.annotation.RestController
@Api("short long link service")
@Slf4j
public class RestController {

    @Autowired
    private ShortLongLinkService shortLongLinkService;




    @RequestMapping("/getShort")
    @ApiOperation("acquire short link by long link.")
    @ApiImplicitParam(name="longLink" ,value="the long link input",dataType = "String")
    public String getShort(@RequestParam("longLink") String longLink){
        log.info("longLink : " + longLink);
        String result = null;
        try{
            result = shortLongLinkService.acquireShortLink(longLink);
        }catch (Exception e){}

        return result ;
    }

    @RequestMapping("/getLong")
    @ApiOperation("acquire long link by short link.")
    @ApiImplicitParam(name="shortLink" ,value="the short link input",dataType = "String")
    public String getLong(@RequestParam("shortLink") String shortLink){
        log.info("shortLink :" + shortLink);
        String result = null;
        try{
            result = shortLongLinkService.acquireLongLink(shortLink);
        }catch (Exception e){}

        return result ;
    }



}
