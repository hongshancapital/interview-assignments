package controller;
import com.demo.Application;
import com.demo.service.URLService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;
import service.URLServiceTest;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by gouyunfei on 2021/4/14.
 */

@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@SpringBootTest(classes=Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@SpringBootTest(classes=Application.class)
public class URLControllerTest {

    private String host = "http://localhost:";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private URLService urlService;
    @Test
    public void testSet() throws UnsupportedEncodingException {
        String url ="xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";

        String json = this.restTemplate.getForObject(host + port   +"/set/"+ url,  String.class);
        System.out.println(json);
    }


    @Test
    public void testGet() throws UnsupportedEncodingException {
        String url ="xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
        String json = this.restTemplate.getForObject(host + port   +"/set/"+ url,  String.class);
        System.out.println(json);


        url ="UnFfqEfy";
        json = this.restTemplate.getForObject(host + port   +"/get/"+ url,  String.class);
        System.out.println(json);
    }


    @Test
    public void setTestMaxHeapSize(){


        for (int i = 0; i < 2024000000; i++) {

        }

        String b= URLServiceTest.createRandomStr1(Math.round(10));
        int flowCounter = 10240000;
        for (int i = 0; i < flowCounter; i++) {
            if(i%10000==0){
                System.out.println(i+"");
            }
            String json = this.restTemplate.getForObject(new StringBuffer().append(host).append(port).append("/set/").append(i).append(b).toString(),  String.class);
            if(json.indexOf("error")>=0){
                break;
            }
        }
        Map<String, Object> context=urlService.getContext();
        System.out.println("冲突个数："+context.get("conflictCount")+"\t"+ (((double)context.get("conflictCount"))/flowCounter));
        System.out.println("一次冲突个数："+context.get("conflictCount1"));
        System.out.println("二次冲突个数："+context.get("conflictCount2"));
        System.out.println("三次冲突个数："+context.get("conflictCount3"));
        System.out.println("reshort次冲突个数："+context.get("conflictCounterReshort"));
    }

}
