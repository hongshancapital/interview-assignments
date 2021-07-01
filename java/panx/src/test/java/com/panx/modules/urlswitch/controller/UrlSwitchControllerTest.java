package com.panx.modules.urlswitch.controller;

import com.panx.model.Result;
import com.panx.modules.urlswitch.vo.UrlVo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
@SuppressWarnings("deprecation")
class UrlSwitchControllerTest{
    @Autowired
    private UrlSwitchController urlSwitchController;
    @Test
    void urlLongToShortWithError() {
        System.out.print("测试错误的url格式\n");
        UrlVo urlVo = new UrlVo();
        urlVo.setShortUrl("cuowuxinxi");
        Result result = urlSwitchController.urlLongToShort(urlVo);
        UrlVo resultUrl = (UrlVo) result.getDatas();
        System.out.print("接口信息:"+result.getResp_msg()+"\n");
        System.out.print("获取结果：shortUrl:"+resultUrl.getShortUrl()+"\nshortUrl:"+resultUrl.getLongUrl()+"\n");
        urlVo = new UrlVo();
        urlVo.setLongUrl("www.baidu.com/");
        result = urlSwitchController.urlLongToShort(urlVo);
        resultUrl = (UrlVo) result.getDatas();
        System.out.print("接口信息:"+result.getResp_msg()+"\n");
        System.out.print("获取结果：shortUrl:"+resultUrl.getShortUrl()+"\nshortUrl:"+resultUrl.getLongUrl()+"\n");
    }

    @Test
    void urlLongToShortWithOutMatch() {
        System.out.print("测试获取无匹配的长域名\n");
        UrlVo urlVo = new UrlVo();
        urlVo.setLongUrl("http://www.baidu.com/asfasuighaiusdg");
        Result result = urlSwitchController.urlLongToShort(urlVo);
        UrlVo resultUrl = (UrlVo) result.getDatas();
        System.out.print("接口信息:"+result.getResp_msg()+"\n");
        System.out.print("获取结果：shortUrl:"+resultUrl.getShortUrl()+"\nlongUrl:"+resultUrl.getLongUrl()+"\n");
    }

    @Test
    void urlShortToLongWithOutMatch() {
        System.out.print("测试获取无匹配的短域名\n");
        UrlVo urlVo = new UrlVo();
        urlVo.setShortUrl("http://www.baidu.com/G9INzGra");
        Result result = urlSwitchController.urlShortToLong(urlVo);
        UrlVo resultUrl = (UrlVo) result.getDatas();
        System.out.print("接口信息:"+result.getResp_msg()+"\n");
        System.out.print("获取结果：shortUrl:"+resultUrl.getShortUrl()+"\nshortUrl:"+resultUrl.getLongUrl()+"\n");
    }

    @Test
    void urlLongToShortWithMatch() {
        System.out.print("测试先录入长域名再用该长域名获取\n");
        UrlVo urlVo = new UrlVo();
        urlVo.setLongUrl("http://www.baidu.com/asfasuighaaaa");
        Result result = urlSwitchController.urlLongToShort(urlVo);
        UrlVo resultUrl = (UrlVo) result.getDatas();
        urlVo = new UrlVo();
        urlVo.setLongUrl("http://www.baidu.com/asfasuighaaaa");
        result = urlSwitchController.urlLongToShort(urlVo);
        System.out.print("接口信息:"+result.getResp_msg()+"\n");
        System.out.print("获取结果：shortUrl:"+resultUrl.getShortUrl()+"\nlongUrl:"+resultUrl.getLongUrl()+"\n");
    }

    @Test
    void urlShortToLongWithMatch() {
        System.out.print("测试先录入短域名再用该短域名获取\n");
        UrlVo urlVo = new UrlVo();
        urlVo.setLongUrl("http://www.baidu.com/asfasuighbbbb");
        Result result = urlSwitchController.urlLongToShort(urlVo);
        UrlVo resultUrl = (UrlVo) result.getDatas();
        urlVo = new UrlVo();
        urlVo.setShortUrl(resultUrl.getShortUrl());
        result = urlSwitchController.urlLongToShort(urlVo);
        System.out.print("接口信息:"+result.getResp_msg()+"\n");
        System.out.print("获取结果：shortUrl:"+resultUrl.getShortUrl()+"\nlongUrl:"+resultUrl.getLongUrl()+"\n");
    }
}