package com.example.demo.controller;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import java.nio.charset.Charset;

import com.google.common.hash.Hashing;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RealtimeHot implements InitializingBean {

    //private static final Logger LOGGER = LoggerFactory.getLogger(RealtimeHot.class);

    //private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    //protected long period;  // 周期更新时间间隔
    protected static int maxSize = 5;

    public static Map<String, String> short2URL = new ConcurrentHashMap<>();
    public static Map<String, String> URL2short = new ConcurrentHashMap<>();
    public static String sep = "龖";

//    protected void start() {
//        period = 100;  //10分钟加载一次
//
//        String sep = ",";
//
//        final Runnable task = new Runnable() {
//            public void run() {
//                cleanOld();// 清理map
//            }
//        };
//        scheduler.scheduleAtFixedRate(task, 0, period, TimeUnit.MINUTES);
//    }
    public static String queryShortFromURL(String tiny) {
        String url = "";
        System.err.println(tiny);
        System.err.println(short2URL.size());
        for(String each:short2URL.keySet()) {
            System.err.println(String.format("key=%s, value=%s", each, short2URL.get(each)));
        }
        if (short2URL.containsKey(tiny)) {
            String res = short2URL.get(tiny);
            String parts[] = res.split(sep);
            //表示有分隔符
            if (parts.length == 2) {
                url = parts[0];
            } else {
                url = res;
            }
        }
        return url;
    }

    public static String writeURL2Short(String url) {
        String current_url = url;
        String des_str = Hashing.murmur3_32().hashString(current_url, Charset.forName("UTF-8")).toString();
        String  post_code = "abcdefghijklmnopqrstuvwxyz";
        int num = 0;
        while (short2URL.containsKey(des_str) && !short2URL.get(des_str).equals(current_url)) {
            String post_fix = post_code.substring(num, num + 1);
            current_url = url + sep + post_fix;
            des_str = Hashing.murmur3_32().hashString(current_url, Charset.forName("UTF-8")).toString();
            num++;
            if (num >= post_code.length()) {
                break;
            }
        }
        if (num >= post_code.length()) {
            //表示循环了超过26次依然冲突,那就随机踢一个
            System.err.println(String.format("url conflict with shorcode! %s <=>%s", current_url, des_str ));
        }
        if (URL2short.size() > maxSize) {
            cleanOld();
        }
        URL2short.put(current_url, des_str);
        short2URL.put(des_str, current_url);
        return des_str;
    }

    public static void cleanOld() {
        //这里为了简单 随便淘汰一个
        if (URL2short.size() >= maxSize) {
            int outnum = URL2short.size() - maxSize + 1;
            int i = 0;
            for (String each : URL2short.keySet()) {
                String url = each;
                String tiny = URL2short.get(url);
                short2URL.remove(tiny);
                URL2short.remove(url);
                i++;
                if (i >= outnum) {
                    return;
                }

            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //start();
    }

}


