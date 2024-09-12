package com.sequoia.web.mapper;

import com.sequoia.web.util.Base62;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SpeedTest {
    private static List<String> caseList = new ArrayList<String>();
    private static StopWatch clock = new StopWatch("mapper写性能测试");
    private static StopWatch clock2 = new StopWatch("mapper读性能测试");


    @BeforeAll
    public static void setUp() {
        Random r = new Random();
        for(long i=0;i<(1<<20);i++) {
            String shortURL = Base62.encode(r.nextLong() >>> 17);
            caseList.add(String.valueOf(shortURL));
        }
    }

    @AfterAll
    public static void report(){
        System.out.println(clock.prettyPrint());
        System.out.println(clock2.prettyPrint());
    }

    @Test
    public void hashMapUrlMapper(){
        UrlMapper mapper = new UrlMapper(UrlMapper.Strategy.hashMap, false, 0,
                0, 0, TimeUnit.DAYS);
        clock.start("hashMap: put测试");
        for(String caseStr : caseList){
            mapper.put(caseStr, "data");
        }
        clock.stop();
        clock2.start("hashMap: get测试");
        for(String caseStr : caseList){
            mapper.get(caseStr);
        }
        clock2.stop();
    }

    @Test
    public void trieMapUrlMapper(){
        UrlMapper mapper = new UrlMapper(UrlMapper.Strategy.trieMap, false, 0,
                0, 0, TimeUnit.DAYS);
        clock.start("trieMap: put测试");
        for(String caseStr : caseList){
            mapper.put(caseStr, "data");
        }
        clock.stop();
        clock2.start("trieMap: get测试");
        for(String caseStr : caseList){
            mapper.get(caseStr);
        }
        clock2.stop();
    }

    @Test
    public void skipListMapUrlMapper(){
        UrlMapper mapper = new UrlMapper(UrlMapper.Strategy.skipListMap, false, 0,
                0, 0, TimeUnit.DAYS);
        clock.start("skipListMap: put测试");
        for(String caseStr : caseList){
            mapper.put(caseStr, "data");
        }
        clock.stop();
        clock2.start("skipListMap: get测试");
        for(String caseStr : caseList){
            mapper.get(caseStr);
        }
        clock2.stop();
    }
}
