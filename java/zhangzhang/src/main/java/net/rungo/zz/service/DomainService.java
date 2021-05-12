package net.rungo.zz.service;

import com.google.common.collect.EvictingQueue;
import lombok.extern.slf4j.Slf4j;
import net.rungo.zz.domain.Domain;
import net.rungo.zz.domain.RequestParam;
import net.rungo.zz.util.ConversionUtils;
import net.rungo.zz.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.*;

@Service
@Slf4j
public class DomainService {

    private static final Map<String, String> DOMAINMAP = new ConcurrentHashMap<>();

    //存放请求的队列
    LinkedBlockingDeque<RequestParam> queue = new LinkedBlockingDeque<>();


    @Resource
    private IdWorker worker;

    //初始化方法
    @PostConstruct
    public void init() {
        //定时执行的线程池，每隔5毫秒执行一次(间隔时间可以由业务决定)，把所有堆积的请求
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(() -> {
            //在这里具体执行批量查询逻辑
            int size = queue.size();
            if (size == 0) {
                //若没有请求堆积，直接返回，等10毫秒再执行一次
                return;
            }
            //若有请求堆积把所有请求都拿出来
            List<RequestParam> requestTests = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                //把请求拿出来
                RequestParam poll = queue.poll();
                requestTests.add(poll);
            }
            //至此请求已经被合并了
            System.out.printf("##############################################\n");
            System.out.printf("请求合并了" + requestTests.size() + "条！\n");
            //组装批量查询条件
            List<String> keyList = new ArrayList<>();
            for (RequestParam requestTest : requestTests) {
                keyList.add(requestTest.getDomainUrl());
            }
            //进行批量查询
            List<Long> idList = worker.nextIdList(keyList.size());
            //把批查结果放入一个map
            Map<String, String> domainMap = new HashMap<>();
            for (int i = 0; i < requestTests.size(); i++) {
                String shortDomain = ConversionUtils.decimalToSixtyTwo(idList.get(i));
                domainMap.put(requestTests.get(i).getDomainUrl(), shortDomain);
                DOMAINMAP.put(requestTests.get(i).getDomainUrl(), shortDomain);
                DOMAINMAP.put(shortDomain, requestTests.get(i).getDomainUrl());
            }

            for (RequestParam requestTest : requestTests) {
                //把放在map中的结果集放回给对应的线程
                //future是对应每个请求的，因为是每个请求线程都传了自己的future是对应的过来
                requestTest.getFuture().complete(DOMAINMAP.get(requestTest.getDomainUrl()));
            }
        }, 0, 5, TimeUnit.MILLISECONDS);
    }


    public String shortDomainSaveHandle(String longDomain) throws ExecutionException, InterruptedException {
        log.info("请求短域名存储接口入参[{}]", longDomain);

        if (DOMAINMAP.containsKey(longDomain)) {
            log.info("缓存中的数据直接返回");
            return DOMAINMAP.get(longDomain);
        }


        CompletableFuture<String> future = new CompletableFuture<>();
        RequestParam requestTest = new RequestParam();
        //把future(把future可以认为是线程间的"传话人")放到等待队列中去，让定时调度的线程池执行并返回值
        requestTest.setFuture(future);
        requestTest.setDomainUrl(longDomain);
        //把requestTest加入等待队列(LinkedBlockingDeque)
        queue.add(requestTest);
        //future(传话人)阻塞直到有值返回
        String shortDomain = future.get();
        log.info("返回的短域名[{}]",shortDomain);
        return shortDomain;
    }


    public String shortDomainReadHandle(String shortDomain) {
        if (DOMAINMAP.containsKey(shortDomain))
            return DOMAINMAP.get(shortDomain);
        return "没有此域名{" + shortDomain + "}对应的长域名，请先请求短域名存储接口";
    }


    public static void main(String[] args) {

        Queue<Domain> fifo = EvictingQueue.create(2);

        System.out.println(fifo);
    }
}
