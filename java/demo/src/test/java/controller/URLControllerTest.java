package controller;
import com.demo.Application;
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


//    @Test
//    public void setTestMaxHeapSize(){
//
//
//        for (int i = 0; i < 2024000000; i++) {
//            String json = this.restTemplate.getForObject(host + port   +"/set/"+ URLServiceTest.createRandomStr1(400),  String.class);
//            if(json.indexOf("error")>=0){
//                break;
//            }
//        }
//
//
//    }

}
