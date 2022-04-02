package com.getao.urlconverter.controller;

import com.getao.urlconverter.dto.vo.GetShortUrlVO;
import com.getao.urlconverter.util.ConverterUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConverterControllerTest {

    private static final String longUrl = "www.google.com";
    private static final String badLongUrl = "12345678";

    @Resource
    ConverterController converterController;

    @Test
    public void testControllerSuccess() {
        GetShortUrlVO vo = converterController.getShortUrl(longUrl);
        assertThat(vo.getShortUrl()).isNotEmpty();
        assertThat(vo.getStatus()).isEqualTo(200);
        System.out.println(vo.getShortUrl());
    }

    @Test
    public void testControllerFail() {
        GetShortUrlVO vo = converterController.getShortUrl(badLongUrl);
        assertThat(vo.getStatus()).isEqualTo(401);
        assertThat(vo.getShortUrl()).isEqualTo(null);
    }

}
