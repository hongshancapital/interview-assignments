package com.domain.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.domain.utils.UUIDUtils;
import lombok.SneakyThrows;
import okhttp3.*;

import java.util.concurrent.*;


public class HttpTest {

    private static String serverUrl="http://127.0.0.1:8080/";

    private static String accessToken="e3853146-a77e-4f51-b52d-277f719a5849";

    private static String post="domain/write";

    private static String get="domain/read";

    private static String[] longurls=new String[]{
            "http://www.qq.com/", "http://www.sina.com/", "http://www.baidu.com/", "http://www.taobao.com/", "http://www.jd.com/"
    };


    public static void main(String[] args) throws Exception {
        //write(); //单写
        //resubmit_write(); //重复写
        //concurrent_write(); //并发写
    }
     static CopyOnWriteArrayList success=new CopyOnWriteArrayList();
     static CopyOnWriteArrayList  fail=new CopyOnWriteArrayList();

    public static  void concurrent_write() throws InterruptedException {
        int thread=100;

        ThreadPoolExecutor executor=new ThreadPoolExecutor(thread,thread,60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10000), Executors.defaultThreadFactory());



       CyclicBarrier barrier=new CyclicBarrier(thread, new Runnable() {
            @Override
            public void run() {
                System.out.println("并发结束"+success.size()+":"+fail.size());
        }
        });
        for(;;){
            for(int i=0;i<thread;i++){
                executor.execute(new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        HttpTest.write();
                        barrier.await();
                    }
                });
            }
            TimeUnit.SECONDS.sleep(1);
        }

    }
    public  static void resubmit_write() throws Exception {
        String url=longurls[0]+ UUIDUtils.getUUID();
        JSONObject request=new JSONObject();
        request.put("longUrl",url);
        String responseStr=post(serverUrl+post,request.toJSONString());
        System.out.println(responseStr);
        TimeUnit.SECONDS.sleep(2);
        String responseStr1=post(serverUrl+post,request.toJSONString());
        System.out.println(responseStr1);
    }
    public  static void write(){
        int max=4,min=0;
        int index = (int) (Math.random()*(max-min)+min);
        String url=longurls[index]+ UUIDUtils.getUUID();
        JSONObject request=new JSONObject();
        request.put("longUrl",url);
        try{
            String responseStr=post(serverUrl+post,request.toJSONString());
            if(responseStr==null)System.out.println(url+": 失败");
            JSONObject response= JSON.parseObject(responseStr);
            System.out.println(url+"      "+response.get("code")+":"+response.get("desc")+"    "+(response.get("data")==null?"":response.getJSONObject("data").get("shortUrl")));
         if(response.getInteger("code")==10)success.add(response.get("code"));
         else fail.add(response.get("code"));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }



    }
    private static String post(String httpsUrl, String context) throws Exception {

        OkHttpClient client = new OkHttpClient();

        MediaType json = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(json, context);

        Request request = new Request.Builder().url(httpsUrl)
                .addHeader("authorization","Bearer "+accessToken)
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new RuntimeException("httpcode:"+response.code());
        }

    }

}
