package com.sequoia.urllink;



import com.google.common.collect.Lists;
import com.sequoia.urllink.bean.GenCodeParam;
import com.sequoia.urllink.util.HttpUtil;
import com.sequoia.urllink.util.JSONUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: chenYing
 * @Date: 2021/5/21 0021 16:05
 */
public class ShortCodeTest {

    @Test
    public void gensTest() throws Exception {
        for (int i = 0; i < 30; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            GenCodeParam param = new GenCodeParam();
                            param.setLongUrl("http://www.hao123.com");
                            param.setAttachList(Lists.newArrayList());
                            for (int i = 0; i < 200; i++) {
                                param.getAttachList().add("159" + RandomStringUtils.randomNumeric(8));
                            }
                            String json = JSONUtil.toJSON(param);
                            String response = HttpUtil.sendPostJson("http://localhost/u/gencodes", json);
                            System.out.println(response);
                        }
                    } catch (Exception e) {

                    }
                }
            }).start();
        }

        TimeUnit.DAYS.sleep(3);

    }

    @Test
    public void genTest() throws Exception {
//        System.out.println("{\"code\":0,\"data\":\"dev-urllink-api.suuyuu.cn/u/fU3yUf\",\"message\":\"成功\"}".length());
        for (int i = 0; i < 1; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Set<String> collect = new HashSet<>();
                        while (true) {
                            String phone = "159" + RandomStringUtils.randomNumeric(8);
                            String response = HttpUtil.sendPostJson("http://localhost/u/gencode?longUrl=15977512212&attach=" + phone, null);
//                            String response = HttpUtil.sendPostJson("https://dev-urllink-api.suuyuu.cn/u/gencode?longUrl=qqqqqqqqqq&attach=" + phone, null);
//                            String response = HttpUtil.sendPostJson("http://localhost/u/gencode?longUrl=1546154", null);
                            if (collect.contains(response)) {
                                System.out.println(response + "   " + phone);
                            }
                            collect.add(response);
                            if (response.length() < 60) {
                                System.out.println(response);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        TimeUnit.DAYS.sleep(3);
    }


    @Test
    public void gen2Test() throws Exception {
        String phone = "159" + RandomStringUtils.randomNumeric(8);
//        String response = HttpUtil.sendPostJson("http://localhost/u/gencode?longUrl=15977512212&attach=" + phone, null);
        String response = HttpUtil.sendPostJson("https://dev-urllink-api.suuyuu.cn/u/gencode?longUrl=qqqsqqqqqqq&attach=" + phone, null);
//                            String response = HttpUtil.sendPostJson("http://localhost/u/gencode?longUrl=1546154", null);
        System.out.println(response);
    }

    @Test
    public void createTableTest() throws Exception {
//        String response = HttpUtil.sendGet("http://localhost/u/new-table");
        String response = HttpUtil.sendGet("https://dev-urllink-api.suuyuu.cn/u/new-table");
        System.out.println(response);
    }
}
