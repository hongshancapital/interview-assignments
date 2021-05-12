package net.rungo.zz.service;

import com.google.common.collect.EvictingQueue;
import lombok.extern.slf4j.Slf4j;
import net.rungo.zz.domain.Domain;
import net.rungo.zz.util.ConversionUtils;
import net.rungo.zz.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class DomainService {

    private static final Map<String, String> DOMAINMAP = new ConcurrentHashMap<>();
//    private static final  Queue<Domain> fifo = EvictingQueue.create(2);        防止内存溢出可以使用队列存储

    @Resource
    private IdWorker worker;

    public String shortDomainSaveHandle(String longDomain) {
        log.info("请求短域名存储接口入参[{}]", longDomain);

        if (DOMAINMAP.containsKey(longDomain)){
            log.info("缓存中的数据直接返回");
            return DOMAINMAP.get(longDomain);
        }

        long id = worker.nextId();
        String shortDomain = ConversionUtils.decimalToSixtyTwo(id);
        log.info("长域名[{}]生成的短域名[{}]", longDomain, shortDomain);
        DOMAINMAP.put(longDomain, shortDomain);
        DOMAINMAP.put(shortDomain, longDomain);
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
