package com.liuxiang.init;

import com.BaseTest;
import com.liuxiang.biz.ShortUrlBiz;
import com.liuxiang.dao.ShortUrlMappingDao;
import com.liuxiang.model.po.ShortUrlMappingPo;
import com.liuxiang.model.view.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;
import javax.annotation.Resource;

/**
 * @author liuxiang6
 * @date 2022-01-09
 **/
@Slf4j
public class InitServiceTest extends BaseTest {

    @Autowired
    private InitService initService;
    @Resource
    private ShortUrlMappingDao shortUrlMappingDao;
    @Autowired
    private ShortUrlBiz shortUrlBiz;

    @Test
    public void init() {
        ShortUrlMappingPo shortUrlMappingPo = new ShortUrlMappingPo();
        shortUrlMappingPo.setShortUrl("abcdefg");
        shortUrlMappingPo.setLongUrl("www.abcdefg.com");
        shortUrlMappingPo.setCreateTime(System.currentTimeMillis());
        shortUrlMappingDao.insert(shortUrlMappingPo);
        initService.init();
    }


    private static String BASE_STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * 生成测试数据
     */
    @Test
    public void testinitData() {

        int i=0;
        while(i<10000){
            Random random = new Random();
            int length = random.nextInt(30);
            String longurl = "http://www.baidu.com/" +random(length);
            CommonResult<String> shortUrl = shortUrlBiz.generateShortUrl(longurl);
            log.info("{}   {}", shortUrl.getMsg(), longurl);
            i++;
        }
    }

    private String random(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < length; i++) {
            int number = random.nextInt(BASE_STR.length());
            sb.append(BASE_STR.charAt(number));
        }
        return sb.toString();
    }
}