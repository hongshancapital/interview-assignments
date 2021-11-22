//package com.liukun.shortdomain.utils;
//
//import org.apache.commons.collections4.BidiMap;
//import org.apache.commons.collections4.bidimap.TreeBidiMap;
//import org.junit.Assert;
//import org.junit.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
///**
// * <p>
// * <b>Class name</b>:
// * </p>
// * <p>
// * <b>Class description</b>: Class description goes here.
// * </p>
// * <p>
// * <b>Author</b>: kunliu
// * </p>
// * <b>Change History</b>:<br/>
// * <p>
// *
// * <pre>
// * Date          Author       Revision     Comments
// * ----------    ----------   --------     ------------------
// * 2021/10/7       kunliu        1.0          Initial Creation
// *
// * </pre>
// *
// * @author kunliu
// * @date 2021/10/7
// * </p>
// */
//@SpringBootTest
//public class UrlExchangeTest {
//    BidiMap<String, String> urlMap = new TreeBidiMap<>();
//
//    @Test
//    public void long2Short() {
//        String url = "https://blog.csdn.net/diaochuangqi7487/article/details/102134868";
//        urlMap.put("url", url);
//        Assert.assertEquals(url, urlMap.get("url"));
//        UrlEncoder urlEncoder = new UrlEncoder();
//        String shortUrl = urlEncoder.shorten();
//        urlMap.put(url, shortUrl);
//        Assert.assertEquals(urlMap.get(url), shortUrl);
//    }
//
//    @Test
//    public void short2Long() {
//        String url = "https://blog.csdn.net/diaochuangqi7487/article/details/102134868";
//        UrlEncoder urlEncoder = new UrlEncoder();
//        String shortUrl = urlEncoder.shorten();
//        urlMap.put(url, shortUrl);
//        Assert.assertEquals(urlMap.getKey(shortUrl), url);
//    }
//
//}