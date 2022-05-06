package com.lenfen.short_domain.service;

import com.lenfen.short_domain.ShortDomainApplication;
import com.lenfen.short_domain.api.TransformController;
import com.lenfen.short_domain.api.entity.ApiResponse;
import com.lenfen.short_domain.bean.ShortDomain;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 应用测试类
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShortDomainApplication.class)
public class ShortDomainApplicationTest {
    @Autowired
    private TransformController transformController;

    /**
     * 展示返回值
     *
     * @param response 接口返回信息
     */
    private void showRes(ApiResponse response) {
        if (response == null) {
            return;
        }
        log.info("返回编码:{}", response.getCode());
        log.info("返回消息:{}", response.getMessage());

        ShortDomain shortDomain = response.getShortDomain();
        if (shortDomain == null) {
            return;
        }

        log.info("短域名:{}", shortDomain.getShortUrl());
        log.info("长域名:{}", shortDomain.getFullUrl());
        log.info("解码次数:{}", shortDomain.getDecodeCount().get());
        log.info("编码次数:{}", shortDomain.getEncodeCount().get());
        log.info("编码时间:{}", shortDomain.getEncodeTime());
    }

    /**
     * 编码方法
     *
     * @param fullUrl 长域名
     */
    private void encode(String fullUrl) {
        ApiResponse res = transformController.encode(fullUrl);
        showRes(res);
    }

    /**
     * 功能测试
     */
    @Test
    void shortDomainTest() {
        //正常编码
        encode("https://www.baidu.com/s?wd=%E6%B2%AA%E6%8C%87%E5%A4%B1%E5%AE%882900%E7%82%B9+%E4%B8%A4%E5%B8%82%E8%BF%91300%E8%82%A1%E8%B7%8C%E5%81%9C&sa=fyb_n_homepage&rsv_dl=fyb_n_homepage&from=super&cl=3&tn=baidutop10&fr=top1000&rsv_idx=2&hisfilter=1");
        decode("dv6A307e");

        //重复编码
        encode("https://lenfen.com/");
        decode("8lsYZ5pV");
        encode("https://lenfen.com/");
        decode("8lsYZ5pV");

        //大于短域名池的编码
        encode("https://fullDomain.com/lalalala/jiujiujiu/hahahah");
        decode("WJJYWTTd");

        // 编码空数据
        encode(null);
        encode("");

        // 解码不存在的数据
        decode("empty123");
        decode("");
        decode(null);

    }

    /**
     * 解码方法
     *
     * @param shortUrl 短域名
     */
    private void decode(String shortUrl) {
        ApiResponse res = transformController.decode(shortUrl);
        showRes(res);
    }
}