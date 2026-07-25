package com.domain.schedule;

import com.domain.bean.DomainValueBean;
import com.domain.service.DomainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ：ji
 * @description：清除过期的数据
 */
@Component
public class ClearSchedule {
    private final static Logger logger = LoggerFactory.getLogger(ClearSchedule.class);
    private ReentrantLock lock = new ReentrantLock();

    @Autowired
    private DomainService domainService;

    /**
     * 每5分钟跑一次定时，清除5分钟前保存的 url
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void clearDomainMap(){
        boolean flag = false;
       try{
           flag = lock.tryLock();
           if (!flag){
               logger.error("clearDomainMap 未获取执行锁，");
               return;
           }
           logger.info("clearDomainMap 任务开始执行");
           // 遍历清除过期key
           ConcurrentHashMap<String,DomainValueBean> domainMap = domainService.getDomainMap();
           Iterator<Map.Entry<String, DomainValueBean>> iterators = domainMap.entrySet().iterator();
           long now = System.currentTimeMillis();
           while (iterators.hasNext()){
               Map.Entry<String, DomainValueBean> next = iterators.next();
               DomainValueBean bean = next.getValue();
               // 五分钟
               if (now - bean.getTime() >= 5 * 60 * 1000){
                   iterators.remove();
                   logger.info("clearDomainMap 清除过期数据key={},value={}",next.getKey(),next.getValue().getUrl());
               }
           }
           logger.info("clearDomainMap 任务执行结束");
       }catch (Exception e){
            logger.error("clearDomainMap 清除过期url定时任务执行异常",e);
       }finally {
           // 如果获取锁，则释放锁
           if (flag){
               lock.unlock();
           }
       }
    }
}
