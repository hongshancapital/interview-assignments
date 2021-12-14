package com.example.urltrans_zhangtao.controller;
import com.example.urltrans_zhangtao.controller.LongToShort;
import com.example.urltrans_zhangtao.entity.LUrl_entity;
import junit.framework.*;

import java.security.NoSuchAlgorithmException;

public class testLongToShort extends TestCase{
    public static String Surl="abcdefg";
    public void testLtoS() throws NoSuchAlgorithmException {
        LongToShort x=new LongToShort();
        LUrl_entity test_ltos;
        test_ltos= x.LTS_Str_g("https://baidu.com");
        Surl=test_ltos.getLUrl_trans();
        System.out.println("测试结果："+test_ltos.getLUrl_trans());
    }
    public void testStoL() {
        LongToShort x=new LongToShort();
        LUrl_entity test_ltos;
        test_ltos= x.STL_Str_g(Surl);
        System.out.println("测试结果："+test_ltos.getLUrl());
    }
}
