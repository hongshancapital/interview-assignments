package com.domain.utils;

import com.domain.config.GlobalParametersConfig;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MappedFileTest {

    @Autowired
    GlobalParametersConfig globalParametersConfig;

    @Test
    public void testFileWriteAndRead() throws InterruptedException {
        MappedFile mappedFile=new MappedFile(globalParametersConfig.getDataFilePath(),"00000000000000",1024*1024*16);

        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                int maxTimes=1000;
                int request=0;
                for(;;){
                    if(request>=maxTimes)return;
                    int max=600,min=10;
                    int ran2 = (int) (Math.random()*(max-min)+min);
                    String data=UUIDUtils.getUUID()+",";
                    System.out.println("写："+mappedFile.writeData(data.getBytes()));
                    TimeUnit.MILLISECONDS.sleep(ran2);
                    request++;

                }
            }
        }).start();

        int maxTimes=1;
        int request=0;
        for(;;){
            if(request>=maxTimes)return;
            int max=600,min=10;
            int ran2 = (int) (Math.random()*(max-min)+min);
            TimeUnit.MILLISECONDS.sleep(ran2);
            System.out.println("读："+mappedFile.readData());
            ran2 = (int) (Math.random()*(max-min)+min);
            TimeUnit.MILLISECONDS.sleep(ran2);
            System.out.println("读："+mappedFile.readData(10,ran2));
            request++;
        }

    }
}
