package com.sequoia.web.service;

import com.sequoia.web.mapper.UrlMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class SpeedTest {
    private static List<String> caseList = new ArrayList<String>(1000);
    private List<String> shortList = new ArrayList<String>(1000);
    private static List<String> historyList = new ArrayList<String>(50);
    private List<String> shortList2 = new ArrayList<String>(50);
    StopWatch clock = new StopWatch("service性能测试");

    @Autowired
    private DWZService service;

    @BeforeAll
    public static void setUp() {
        BufferedReader testUrls = new BufferedReader(new InputStreamReader(SpeedTest.class.getResourceAsStream(
                "/url_case.txt")));
        String url = null;
        try {
            while ((url = testUrls.readLine()) != null) {
                caseList.add(url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader historyUrls = new BufferedReader(new InputStreamReader(SpeedTest.class.getResourceAsStream(
                "/history.txt")));
        try {
            while ((url = historyUrls.readLine()) != null) {
                historyList.add(url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void hashMapUrlMapper(){
        UrlMapper mapper = new UrlMapper(UrlMapper.Strategy.hashMap, false, 0,
                0, 0, TimeUnit.DAYS);
        clock.start("写测试: 域名Only 1000");
        for(String caseStr : caseList){
            shortList.add(service.saveShortUrlByLongUrl(caseStr));
        }
        clock.stop();
        clock.start("写测试: 真实浏览记录 50");
        for(String caseStr : historyList){
            shortList2.add(service.saveShortUrlByLongUrl(caseStr));
        }
        clock.stop();
        clock.start("读测试: 域名Only 1000");
        for(String caseStr : shortList){
            service.getLongUrlByShortUrl(caseStr);
        }
        clock.stop();
        clock.start("读测试: 真实浏览记录 50");
        for(String caseStr : shortList2){
            service.getLongUrlByShortUrl(caseStr);
        }
        clock.stop();
        System.out.println(clock.prettyPrint());
    }

}
