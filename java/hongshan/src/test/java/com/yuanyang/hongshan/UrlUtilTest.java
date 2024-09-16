package com.yuanyang.hongshan;
import com.yuanyang.hongshan.util.UrlCacheUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Assert;

@SpringBootTest
@RunWith(SpringRunner.class)

public class UrlUtilTest {
    @Autowired
    private UrlCacheUtil urlCacheUtil;

    @Test
    public void testPut() {
        Boolean res = urlCacheUtil.put("https://baike.baidu.com/item/%E7%BA%A2%E6%9D%89%E8%B5%84%E6%9C%AC/9915610?fr=aladdin", "hongshan");
        Assert.assertTrue("添加成功", res);
    }

    @Test
    public void testGetLongUrl() {
        Boolean res = urlCacheUtil.put("hongshan","https://baike.baidu.com/item/%E7%BA%A2%E6%9D%89%E8%B5%84%E6%9C%AC/9915610?fr=aladdin");
        String longUrl = urlCacheUtil.get("hongshan");
        System.out.println(longUrl);
        Assert.assertEquals("获取长域名正确", "https://baike.baidu.com/item/%E7%BA%A2%E6%9D%89%E8%B5%84%E6%9C%AC/9915610?fr=aladdin", longUrl);
    }

    @Test
    public void testRemove() {
        urlCacheUtil.put("hongshan","https://baike.baidu.com/item/%E7%BA%A2%E6%9D%89%E8%B5%84%E6%9C%AC/9915610?fr=aladdin");
        Boolean res = urlCacheUtil.remove("hongshan");
        Assert.assertTrue("删除成功", res);
    }

    @Test
    public void  testRemoveEldestEntry(){
        for (int i = 1; i <=10001 ; i++) {
            urlCacheUtil.put(String.valueOf(i),String.valueOf(i));
        }
        Assert.assertEquals("1已经失效", "", urlCacheUtil.get("1"));
        Assert.assertEquals("10001插入", "10001", urlCacheUtil.get("10001"));
    }
}
