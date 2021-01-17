package com.wyd.http.net;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.SystemDefaultHttpClient;

import java.io.IOException;
import java.util.concurrent.*;

public class MulitRequestTest {

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 5,
                1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(1000));
        executor.execute(()->{
            while (true) {
                try {
                    CloseableHttpClient build = HttpClientBuilder.create().build();
                    HttpPost httpPost = new HttpPost("http://localhost:3557/pom.xml");
                    CloseableHttpResponse execute = build.execute(httpPost);
                    execute.getEntity().getContent().close();
                    execute.close();
                    build.close();
                    build=null;
                    httpPost=null;
                    execute=null;
                    Thread.sleep(2000);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


    }
}
