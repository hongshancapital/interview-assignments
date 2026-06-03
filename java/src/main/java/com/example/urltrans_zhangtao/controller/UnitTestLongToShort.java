package com.example.urltrans_zhangtao.controller;

import com.example.urltrans_zhangtao.entity.LUrl_entity;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;

@SpringBootTest
public class UnitTestLongToShort {
    public static String Surl="abcdefg";
    UnitTestLongToShort unitTestLongToShort;
    @Rule
    public ContiPerfRule contiPerfRule=new ContiPerfRule();
    @Test
//    @PrefTest(invocations=100,threads=10)
    public void test() throws NoSuchAlgorithmException {
        String testLUrl="https://baidu.com";
        LongToShort x=new LongToShort();
        LUrl_entity test_ltos;
        test_ltos= x.LTS_Str_g(testLUrl);
        Surl=test_ltos.getLUrl_trans();
        System.out.println("测试结果："+test_ltos.getLUrl_trans());

        test_ltos= x.STL_Str_g(Surl);
        String r=test_ltos.getLUrl();
        System.out.println("测试结果："+r);

        Assert.assertEquals(testLUrl,r);
    }
}
