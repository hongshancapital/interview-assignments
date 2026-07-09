package com.domain.controller;

import com.domain.api.controller.domain.DomainController;
import com.domain.api.request.domain.DomainReadRequest;
import com.domain.api.request.domain.DomainWriteRequest;
import com.domain.api.response.BaseResponse;
import com.domain.api.response.domian.DomainReadResponse;
import com.domain.api.response.domian.DomainWriteResponse;
import com.domain.utils.UUIDUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DomainControllerTest {

    @Autowired
    private DomainController domainController;

    private String[] longurls=new String[]{
            "http://www.qq.com/", "http://www.sina.com/", "http://www.baidu.com/", "http://www.taobao.com/", "http://www.jd.com/"
    };

    @Test
    public void write() throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int max=4,min=0;
                int index = (int) (Math.random()*(max-min)+min);
                String longUrl=longurls[index]+ UUIDUtils.getUUID();
                DomainWriteRequest request=new DomainWriteRequest();
                request.setLongUrl(longUrl);
                BaseResponse<DomainWriteResponse> response=domainController.write(request);
            }
        }).start();


        TimeUnit.SECONDS.sleep(5*1);
    }
    @Test
    public void read(){
        DomainReadRequest request=new DomainReadRequest();
        request.setShortUrl("http://ttt.cn/qqyVBeyF");
        BaseResponse<DomainReadResponse> response=domainController.read(request);
    }
}
