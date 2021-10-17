package com.zhangzheng.homework.entity;

import com.zhangzheng.homework.ApplicationTests;
import org.junit.Test;

import java.util.Date;

/**
 * @author zhangzheng
 * @version 1.0
 * @description: TODO
 * @date 2021/10/11 下午5:45
 */
public class UrlMapTest extends ApplicationTests {

    @Test
    public void urlMap(){
        new UrlMap(2l,"","",new Date(),new Date());
        UrlMap urlMap = new UrlMap();
        urlMap.setId(1l);
        urlMap.setLongUrl("http://www.ewqr.com/qeqwed/adsfdsf");
        urlMap.setShortUrl("dafge12");
        urlMap.setModifyTime(new Date());
        urlMap.setCreateTime(new Date());
        urlMap.getLongUrl();
        urlMap.getShortUrl();
        urlMap.getCreateTime();
        urlMap.getId();
        urlMap.getModifyTime();
        urlMap.equals("");
        urlMap.equals(urlMap);
        urlMap.hashCode();
        urlMap.toString();
        urlMap.canEqual("");
    }
}
