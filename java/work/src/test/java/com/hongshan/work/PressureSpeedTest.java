package com.hongshan.work;

import com.hongshan.work.mapper.HashMapUrlMap;
import com.hongshan.work.util.ConversionUtils;
import com.hongshan.work.util.SequenceGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class PressureSpeedTest {
    private static List<String> caseList = new ArrayList<String>();
    private static StopWatch putSw = new StopWatch("mapper写性能测试");
    private static StopWatch getSw = new StopWatch("mapper读性能测试");

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @PostConstruct
    public void init(){
        for(long i=0;i<(1<<20);i++) {
            long sequence = sequenceGenerator.generate();
            String code = ConversionUtils.X.encode62(sequence);
            caseList.add(code);
        }
    }

    @Test
    public void hashMapUrlMap(){
        HashMapUrlMap map = new HashMapUrlMap(false, 0,
                0, 0,TimeUnit.DAYS);
        putSw.start("hashMap: put测试");
        for(String caseStr : caseList){
            map.put(caseStr, "data");
        }
        putSw.stop();
        getSw.start("hashMap: get测试");
        for(String caseStr : caseList){
            map.get(caseStr);
        }
        getSw.stop();
        System.out.println("putSw.prettyPrint()~~~~~~~~~~~~~~~~~");
        System.out.println(putSw.prettyPrint());

        System.out.println("get.prettyPrint()~~~~~~~~~~~~~~~~~");
        System.out.println(getSw.prettyPrint());
    }
}