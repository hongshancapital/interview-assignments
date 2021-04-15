package service;

import com.demo.Application;
import com.demo.service.URLService;
import com.demo.service.impl.URLServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

/**
 * Created by gouyunfei on 2021/4/14.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
public class URLServiceTest {

    @Test
    public void contextLoads() {
        System.out.println("xxxx");
    }
    @Autowired
    private URLServiceImpl urlService;

    @Test
    public void setTest(){
        for(int i=0;i<10;i++) {
            String b = createRandomStr1(1024);
            String shortUrl = urlService.setUrl(b);
            if(shortUrl==null)break;
            assert (shortUrl.length() == 8);
            System.out.println(shortUrl);
        }

    }

    @Test
    public void setTestMaxHeapSize(){
        String b= createRandomStr1(1024*1024);;
        String shortUrl=null;
        for (int i = 0; i < 10240; i++) {

            shortUrl= urlService.setUrl(new StringBuffer().append(i).append(b).toString());
            if(shortUrl==null){
                System.out.println("超过内存大小");
                break;
            }

        }


    }


    @Test
    public void reShort(){
        String b= createRandomStr1(1400);;

        String sb = urlService.reShort(b);
        if(sb==null)return;
        assert (sb.length() == 8);
        System.out.println(sb);
    }

    @Test
    public void getTest(){
        String b= createRandomStr1(1400);
        String sb = urlService.setUrl(b);
        if(sb==null)return;
        assert (sb.length() == 8);
        System.out.println(sb);

        String longUrl = urlService.getUrl(sb);
        assert(longUrl.equals(b));
    }



    public static String createRandomStr1(int length){
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            stringBuffer.append(str.charAt(number));
        }
        return stringBuffer.toString();
    }

}
