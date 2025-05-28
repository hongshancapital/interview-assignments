package com.gege.assignment;

import org.apache.tomcat.util.threads.TaskQueue;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
@SpringBootTest
class AssignmentApplicationTests {
    @Autowired
    AssignmentApplication assignmentApplication;
    private static final Long MAX_MAP_SIZE = 10000L;
    String urlStr = "https://github.com/scdt-china/interview-assignments";

    @Test
    void testGetShort() {
        String shortUrl = assignmentApplication.getShortUrl(urlStr);
        String url = assignmentApplication.getUrl(shortUrl);
        System.out.println(shortUrl);
        System.out.println(url);
    }

    @Test
    void testGetShortBath() {
        for(int i=0;i<=MAX_MAP_SIZE;i++){
            urlStr = UUID.randomUUID().toString();
            assignmentApplication.getShortUrl(urlStr);
        }
        System.out.println("ok");
    }


}
