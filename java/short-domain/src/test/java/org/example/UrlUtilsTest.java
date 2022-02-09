package org.example;

import org.example.utils.UrlUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UrlUtilsTest {

    @Test
    public void check_null_test() {
        Assertions.assertFalse(UrlUtils.checkUrl(null));
        Assertions.assertFalse(UrlUtils.checkUrl(""));
    }

    @Test
    public void check_true_test() {
        Assertions.assertTrue(UrlUtils.checkUrl("http://www.baidu.com"));
        Assertions.assertTrue(UrlUtils.checkUrl("https://www.baidu.com"));
        Assertions.assertTrue(UrlUtils.checkUrl("https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=%E7%BA%A2%E8%A1%AB%E4%B8%AD%E5%9B%BD&fenlei=256&oq=%25E7%25BA%25A2%25E8%25A1%25AB%25E4%25B8%25AD%25E5%259B%25BD%25E5%2592%258C%25E7%25BA%25A2%25E6%259D%2589%25E7%259A%2584%25E5%2585%25B3%25E7%25B3%25BB&rsv_pq=87fbad3a0016c3b9&rsv_t=a27dqUeqGr0f6n3tF4gSa8mhYGrQ44xD6NVUTDf6tOYwj6hpZZno0U5b4AM&rqlang=cn&rsv_dl=tb&rsv_enter=1&rsv_btype=t&inputT=225&rsv_sug3=33&rsv_sug1=38&rsv_sug7=100&rsv_sug2=0&rsv_sug4=858&rsv_sug=1"));
    }

    @Test
    public void check_false_test() {
        Assertions.assertFalse(UrlUtils.checkUrl("abcd"));
        Assertions.assertFalse(UrlUtils.checkUrl("http://a"));
    }
}
