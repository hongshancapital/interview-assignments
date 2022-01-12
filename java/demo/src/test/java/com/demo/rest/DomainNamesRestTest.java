package com.demo.rest;

import com.demo.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author syd
 * @description
 * @date 2022/1/12
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DomainNamesRestTest {
    @Autowired
    DomainNamesRest domainNamesRest;

    @Test
    public void startTest() {
        shortenTest1();
        shortenTest2();
        shortenTest3();

        longUrlTest1();
        longUrlTest2();
    }

    private void shortenTest1() {
        String longUrl = "http://127.0.0.1:8080/index?name=abcdefghjk8ndd&password=xnhqywa823uydbnfhfl&sid=mmmaokksk";
        ResponseDTO response = domainNamesRest.shorten(longUrl);
        log.info("{}", response.getData().toString());
    }

    private void shortenTest2() {
        String longUrl = "";
        ResponseDTO response = domainNamesRest.shorten(longUrl);
    }

    private void shortenTest3() {
        String longUrl = "http://127.0.0.1:8080/index?name=abcdefghjk8ndd&password=xnhqywa823uydbnfhfl&sid=mmmaokksk/";
        ResponseDTO response = domainNamesRest.shorten(longUrl);
        domainNamesRest.longUrl(response.getData().toString());
    }

    private void longUrlTest1() {
        String shortUrl = "http://127.0.0.1:8080/eP5U4MEH/";
        ResponseDTO response = domainNamesRest.longUrl(shortUrl);
        log.info("{}", response.toString());
    }

    private void longUrlTest2() {
        String shortUrl = "";
        ResponseDTO response = domainNamesRest.longUrl(shortUrl);
        log.info("{}", response.toString());
    }
}