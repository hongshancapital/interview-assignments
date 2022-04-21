package com.ittest.springboot;

import com.ittest.service.DomainService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @Author: taojiangbing
 * @Date: 2022/4/20 19:13
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DominTest {

    @Resource
    private DomainService domainService;

    private CountDownLatch countDownLatch = new CountDownLatch(10000);

    private CountDownLatch transforCountDownLatch = new CountDownLatch(10000);


    @Test
    public void storageTest() {
        String longDomain = "www.baidu.com";
        String shortDomain = domainService.storage(longDomain);
        System.out.println(shortDomain);
    }

    @Test
    public void transforTest() {
        String shortDomain = "VMxnoUKF";
        String longDomain = domainService.transfor(shortDomain);
        System.out.println(longDomain);
    }

    @Test
    public void storageAndTransforTest() {

        // 1.存储
        String longDomain = "www.baidu.com";
        String shortDomain = domainService.storage(longDomain);
        System.out.println(shortDomain);

        // 2.获取
        String longDomainResult = domainService.transfor(shortDomain);
        System.out.println(longDomainResult);
    }

    @Test
    public void storageThreadTest() throws InterruptedException {
        Long start = System.currentTimeMillis();
        // 并发测试,保证最大线程数，模拟最大并发到10000
        LinkedBlockingQueue blockingQueue = new LinkedBlockingQueue(20);
        ExecutorService executorService = new ThreadPoolExecutor(50, 10000,
                0L, TimeUnit.MILLISECONDS,
                blockingQueue);

        List<String> list = new ArrayList(16);
        List<String> result = new ArrayList<>();
        list.add("https://github.com/scdt-china/interview-assignments/tree/master/java");
        list.add("https://www.baidu.com/s?wd=java%20map%E4%B8%AD%E6%A0%B9%E6%8D%AEvalue%E6%9F%A5%E8%AF%A2key&rsv_spt=1&rsv_iqid=0xa987332d00007fba&issp=1&f=8&rsv_bp=1&rsv_idx=2&ie=utf-8&rqlang=cn&tn=78000241_9_hao_pg&rsv_enter=1&rsv_dl=tb&oq=map%25E4%25B8%25AD%25E6%25A0%25B9%25E6%258D%25AEvalue%25E6%259F%25A5%25E8%25AF%25A2key&rsv_btype=t&inputT=1090&rsv_t=3058Oo6nNjx9TM79o7lheMEJ%2Bpm7BB6f3hbKgUCEoQEL3RV5mOfaoKNGwyAXHPqUoVsbHG9hsiE&rsv_sug3=53&rsv_sug1=41&rsv_sug7=100&rsv_pq=a7895f240000d776&rsv_sug2=0&rsv_sug4=1709");
        list.add("https://www.iduba.com/sv.html?f=chedhntab");
        list.add("http://localhost:8888/swagger-ui.html#/domain-controller/storageUsingGET");
        list.add("http://pm.stnts.com/project/202204/31269936f9cc47ef819f6eb1726da116/%E6%96%B0SaaS1.0.4/#id=jokjvz&p=%E5%AD%98%E8%B4%A7%E5%91%A8%E8%BD%AC%E5%88%97%E8%A1%A8_1&g=1");
        list.add("https://www.nowcoder.com/exam/oj/ta?page=3&tpId=37&type=37");
        list.add("https://chijiweb.eiyoo123.cn/#/index");
        list.add("https://chijiweb.eiyoo123.cn/#/index");
        list.add("http://sms-admin-test.jiezhansifang.com/query/sms-list");
        list.add("https://www.bilibili.com/video/BV1rL4y1u74D?p=2");
        Random random = new Random(10);
        for (int i = 0; i < 100000; i++) {
            executorService.execute(() -> {
                try {
                    String shortDomain = domainService.storage(list.get(random.nextInt(10)) + System.currentTimeMillis());
                    result.add(shortDomain);
                    System.out.println(Thread.currentThread().getName() + "号线程,长连接转换成短连接结果:" + shortDomain);
                    countDownLatch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        Long end = System.currentTimeMillis();
        countDownLatch.await();
        System.out.println("1w并发存入长连接的集合大小：" + result.size());
        System.out.println("1w并发存入长连接花费时间：" + (end - start));
        // 睡眠5秒观察日志
        Thread.sleep(5000);

        Long transforStart = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            executorService.execute(() -> {
                try {
                    String shortDomain = domainService.transfor(result.get(random.nextInt(result.size())));
                    System.out.println(Thread.currentThread().getName() + "号线程,短连接转换成长连接结果:" + shortDomain);
                    transforCountDownLatch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        transforCountDownLatch.await();
        Long transforEnd = System.currentTimeMillis();
        System.out.println("1w并发查询短连接转换成长连接花费时间：" + (transforEnd - transforStart));
    }

    @Test
    public void mapPutTest() {
        List<String> list = new ArrayList<>();
        Random random = new Random(10);
        list.add("https://github.com/scdt-china/interview-assignments/tree/master/java");
        list.add("https://www.baidu.com/s?wd=java%20map%E4%B8%AD%E6%A0%B9%E6%8D%AEvalue%E6%9F%A5%E8%AF%A2key&rsv_spt=1&rsv_iqid=0xa987332d00007fba&issp=1&f=8&rsv_bp=1&rsv_idx=2&ie=utf-8&rqlang=cn&tn=78000241_9_hao_pg&rsv_enter=1&rsv_dl=tb&oq=map%25E4%25B8%25AD%25E6%25A0%25B9%25E6%258D%25AEvalue%25E6%259F%25A5%25E8%25AF%25A2key&rsv_btype=t&inputT=1090&rsv_t=3058Oo6nNjx9TM79o7lheMEJ%2Bpm7BB6f3hbKgUCEoQEL3RV5mOfaoKNGwyAXHPqUoVsbHG9hsiE&rsv_sug3=53&rsv_sug1=41&rsv_sug7=100&rsv_pq=a7895f240000d776&rsv_sug2=0&rsv_sug4=1709");
        list.add("https://www.iduba.com/sv.html?f=chedhntab");
        list.add("http://localhost:8888/swagger-ui.html#/domain-controller/storageUsingGET");
        list.add("http://pm.stnts.com/project/202204/31269936f9cc47ef819f6eb1726da116/%E6%96%B0SaaS1.0.4/#id=jokjvz&p=%E5%AD%98%E8%B4%A7%E5%91%A8%E8%BD%AC%E5%88%97%E8%A1%A8_1&g=1");
        list.add("https://www.nowcoder.com/exam/oj/ta?page=3&tpId=37&type=37");
        list.add("https://chijiweb.eiyoo123.cn/#/index");
        list.add("https://chijiweb.eiyoo123.cn/#/index");
        list.add("http://sms-admin-test.jiezhansifang.com/query/sms-list");
        list.add("https://www.bilibili.com/video/BV1rL4y1u74D?p=2");
        for (int i = 0; i < 10000; i++) {
            domainService.storage(list.get(random.nextInt(10)) + System.currentTimeMillis());
        }
    }
}
