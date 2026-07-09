package com.domain.service;

import com.domain.po.StorePO;
import com.domain.service.stroe.StoreService;
import com.domain.utils.ShortUrlGenerator;
import com.domain.utils.UUIDUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class StoreServiceTest {

    @Autowired
    private StoreService storeService;

    private String[] longurls=new String[]{
            "http://www.qq.com/", "http://www.sina.com/", "http://www.baidu.com/", "http://www.taobao.com/", "http://www.jd.com/"
    };

    @Test
    public void write() throws InterruptedException {
        int maxTimes=1000;
        int request=0;
        for(;;){
            if(request>=maxTimes)return;
            int max=4,min=0;
            int index = (int) (Math.random()*(max-min)+min);
            String longUrl=longurls[index]+ UUIDUtils.getUUID();
            StorePO storePO=new StorePO();
            storePO.setLongUrl(longUrl);
            storePO.setAddressCode(ShortUrlGenerator.generator(storePO.getLongUrl()));
            storePO.setShortUrl("http://tl.cn/"+storePO.getAddressCode());
            storeService.save(storePO);
            System.out.println(storePO);
            int sleep_max=5,sleep_min=1;
            int ran = (int) (Math.random()*(sleep_max-sleep_min)+sleep_min);
            TimeUnit.MILLISECONDS.sleep(ran);
            request++;
        }

    }
}
