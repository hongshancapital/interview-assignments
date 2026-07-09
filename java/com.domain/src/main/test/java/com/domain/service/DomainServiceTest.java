package com.domain.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.domain.bo.DomainBO;
import com.domain.config.GlobalParametersConfig;
import com.domain.service.domain.DomainService;
import com.domain.utils.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DomainServiceTest {

    @Autowired
    private DomainService domainService;

    @Autowired
    private GlobalParametersConfig globalParametersConfig;  //全局配置

    private String[] longurls=new String[]{
            "http://www.qq.com/", "http://www.sina.com/", "http://www.baidu.com/", "http://www.taobao.com/", "http://www.jd.com/"
    };

    @Test
    public void createCode() throws InterruptedException {
        int maxTimes=1000;
        int request=0;
        for(;;){
            if(request>=maxTimes) return;
            int max=4,min=0;
            int index = (int) (Math.random()*(max-min)+min);
            String longUrl=longurls[index]+ UUIDUtils.getUUID();
            DomainBO domainBO=new DomainBO();
            domainBO.setLongUrl(longUrl);
            domainBO=domainService.createShortDomain(domainBO);
            System.out.println(domainBO);
            int sleep_max=5,sleep_min=1;
            int ran = (int) (Math.random()*(sleep_max-sleep_min)+sleep_min);
            TimeUnit.MILLISECONDS.sleep(ran);
            request++;
        }
    }

    @Test
    public void getLongDomain() throws Exception {
        String path=globalParametersConfig.getDataFilePath();  //数据目录
        File dir = new File(path);
        File[] array = dir.listFiles();
        List<String> shortUrls=new ArrayList<>();
        for(File file:array){
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuffer buffer=new StringBuffer();
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                buffer.append(tempString);
            }
            String[] datas=buffer.toString().split("\\$");
            for(String data:datas){
                data=data.trim();
                if(!StringUtils.isEmpty(data)){
                    byte[] bytes= Base64.getDecoder().decode(data.getBytes());
                    String base64=new String(bytes);
                    JSONObject jsonObject = JSON.parseObject(base64);
                    DomainBO domainBO=JSON.toJavaObject(jsonObject,DomainBO.class);
                    shortUrls.add(domainBO.getShortUrl());
                }
            }

        }
        for(String shortUrl:shortUrls){
            DomainBO domainBO=new DomainBO();
            domainBO.setShortUrl(shortUrl);
            domainBO=domainService.getLongDomain(domainBO);
            if(domainBO.getIsSuccess())
            System.out.println(domainBO);
        }


    }
}
