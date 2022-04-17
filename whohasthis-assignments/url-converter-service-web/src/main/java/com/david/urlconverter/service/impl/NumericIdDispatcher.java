package com.david.urlconverter.service.impl;

import com.david.urlconverter.model.web.IdRange;
import com.david.urlconverter.service.dubbo.IUrlConverterIDRangeSOAService;
import com.david.urlconverter.utils.ConverterUtils;
import com.david.urlconverter.utils.UrlUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  号段分发器
 */
@Component
@Accessors(chain = true)
@Slf4j
@Setter
@Getter
@ToString
public class NumericIdDispatcher {


    @DubboReference(version = "${urlConverter.service.version}")
    private IUrlConverterIDRangeSOAService urlConverterIDRangeSOAService;

    /**
     * 号段分发woker数量
     */
    private static final int WORKER_COUNT  = 10;

    /**
     * 当前使用的号段范围
     */
    private volatile IdRange currentIdRange;

    /**
     * 在当前号段可用数少于1000时，提前从发号服务获取下个可用号段
     *
     */
    private volatile IdRange nextIdRange;


    /**
     * 记录当前可用号段数
     */
    private volatile AtomicInteger avaiableCount;

    /**
     * 号段分发worker组
     */
    private List<NumericIdDispatcherWorker> workerGroup;


    /**
     * 初始化worker组
     */
    @PostConstruct
    public synchronized void initWorkerGroup(){
        if (avaiableCount != null && avaiableCount.get() > 0) {
            return;
        }

        if (currentIdRange == null) {
            if(nextIdRange!=null){
                setCurrentIdRange(nextIdRange);
            }else{
                setCurrentIdRange(retrieveNextRange());
            }
            //重置nextIdRange
            nextIdRange = null;
        }

        workerGroup = new ArrayList<>();
        for(int i=0;i< WORKER_COUNT;i++){
            NumericIdDispatcherWorker worker = new NumericIdDispatcherWorker();
            LinkedList<Long> list = new LinkedList<>();
            for(long num= currentIdRange.getStartNum()+i;num< currentIdRange.getEndNum();num=num+WORKER_COUNT){
                list.add(num);
            }
            worker.setIdList(list);
            workerGroup.add(worker);
        }
        avaiableCount = new AtomicInteger((int)(currentIdRange.getEndNum()- currentIdRange.getStartNum()));
    }

    /**
     * 从worker组中随机选择一个worker,  取出下一个可用的Id
     * @return
     */
    public synchronized String getNextId(){
        Long nextId = null;

        if (avaiableCount.get() == 0) {
            log.info("inside init branch");
            currentIdRange = null;
            avaiableCount = null;
            initWorkerGroup();
        }

        nextId = getNextAvailableId();
        log.info("get id result:{}, availableCount:{}", nextId ,avaiableCount.get());

        return UrlUtils.setPlaceHolder(ConverterUtils.convertNumber(nextId));
    }

    private Long getNextAvailableId() {

        Random random = new Random();
        int workerId = random.nextInt(workerGroup.size());
        Long nextId = workerGroup.get(workerId).dispatchId();
        /**
         * 当前随机到的worker号段已经发空,移除当前worker
         * 从头开始遍历,找到第一个有可用号段的worker
         */
        if (nextId == null) {
            for (NumericIdDispatcherWorker worker : workerGroup) {
                if (!worker.isEmpty()) {
                    if (!worker.getIdList().isEmpty()) {
                        avaiableCount.decrementAndGet();
                        nextId = worker.dispatchId();
                        break;
                    }
                }
            }
        }else{
            avaiableCount.decrementAndGet();
        }

        /**
         * 当前可用号段小于1000时，准备下一号段
         */
        if(avaiableCount.get()<=1000){
            new Thread(() -> {
                if(nextIdRange==null){
                    populateNextRange();
                }
            }).start();
        }


        return nextId;
    }

    public synchronized void populateNextRange(){
        if(nextIdRange==null){
            log.info("start populate next range");
            nextIdRange = retrieveNextRange();
        }
    }

    /**
     * 从url-converter-service中获取下个可用的号段范围
     */
    private IdRange retrieveNextRange(){
        return urlConverterIDRangeSOAService.getNextAvailableRange();
    }
}
