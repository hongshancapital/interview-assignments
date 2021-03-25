package com.shorturl;

import com.shorturl.utils.UrlUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by panruohan on 21/3/23.
 */
public class UrlUtilsTest {

    @Test
    public void normalizeUrl() throws Exception {
        String url = "https://www.baidu.com/s?ie=utf-8&f=3&rsv_bp=1&rsv_idx=1&tn=baidu&wd=groovy%E8%AF%AD%E6%B3%95&oq=groovy%2520%25E6%2596%25B9%25E6%25B3%2595&rsv_pq=8ad1c25a00006c3c&rsv_t=e4e5JATOC77wl6c6P5qqluO5BXGf199MMtlwNVqS63QeGuF589J7k0orzng&rqlang=cn&rsv_enter=1&rsv_sug3=4&rsv_sug1=1&rsv_sug7=100&rsv_sug2=1&prefixsug=groovy&rsp=3&inputT=3673&rsv_sug4=3970";
        Assert.assertEquals(url, UrlUtils.normalizeUrl(url));
        System.out.println(UrlUtils.normalizeUrl(url));
    }
}