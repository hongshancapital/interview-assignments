package com.yujianfei;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileOutputStream;

@SpringBootTest(classes = ShortDNServiceApplication.class)
@RunWith(SpringRunner.class)
public class GeneratePerformanceFileTest {

    @Test
    public void writeUrlFileToPaper() throws Exception {
        FileOutputStream fos = new FileOutputStream("url.txt", true);
        String str = "";
        for (int i = 1; i <= 100000; i++) {
            str += "" + i + "\t\n";
        }
        byte[] output = str.getBytes();
        fos.write(output);
        fos.close();
    }

}
