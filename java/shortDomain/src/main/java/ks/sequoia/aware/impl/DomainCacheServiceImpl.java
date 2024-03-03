package ks.sequoia.aware.impl;

import ks.sequoia.eobj.DomainEObj;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author jing.tong
 * @since 2022-01-08
 */
@Service
public class DomainCacheServiceImpl extends AbstractCacheServiceImpl {
    @Resource
    private TaskExecutor threadPoolExecutor;

    @Override
    public DomainEObj queryEObjByLongDomain(String longDomain) {
        if(longDomain == null){
            return null;
        }
        DomainEObj domainEObj = longMappingMap.get(longDomain);
        if (domainEObj == null) {
            DomainEObj domain = this.getDomainBObj().queryEObjByLongDomain(longDomain);
            //说明数据库也没有数据
            if(domain == null){
                domain =  transformShortDomain(longDomain);
                //使用异步线程放到缓存中去,遵循最近最少使用原则.
                submitTail(domain);
            }
            return domain;
        } else {
            //高并发情况下 ,防止高并发,并将最近访问的更新到尾部
            submitHead(domainEObj);
            return domainEObj;
        }
    }

    private void submitTail(final DomainEObj domainEObj) {
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                updateCache(domainEObj);
            }
        });
    }
    private void submitHead(final DomainEObj domainEObj) {
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    lruList.remove(domainEObj.getDomainId());
                    lruList.put(domainEObj.getDomainId(), domainEObj);
                }
            }
        });
    }

    private synchronized void updateCache(DomainEObj domainEObj) {
        if (lruList.size() > INITIAL_CAPACITY) {
            throw new RuntimeException("LRU List beyond range " + INITIAL_CAPACITY);
        }
        if(lruList.size()< INITIAL_CAPACITY){
            lruList.put(domainEObj.getDomainId(), domainEObj);
            return;
        }
        //由于HashMap Key具有唯一性，所以并发下重复添加不会重复。
        Map.Entry<Long,DomainEObj> domainEObjEntry = lruList.getHead();
        if(domainEObjEntry == null){
            lruList.put(domainEObj.getDomainId(), domainEObj);
        }else {
           DomainEObj domain =  domainEObjEntry.getValue();
           longMappingMap.remove(domain.getLongDomain());
           shortMappingMap.remove(domain.getShortDomain());
           longMappingMap.put(domainEObj.getLongDomain(),domainEObj);
           shortMappingMap.put(domainEObj.getShortDomain(),domainEObj);
        }
    }

    @Override
    public DomainEObj queryEObjByShortDomain(String shortDomain) {
        if (StringUtils.isEmpty(shortDomain)) {
            throw new RuntimeException("long domain name is not exist");
        }
        DomainEObj domainEObj = shortMappingMap.get(shortDomain);
        if (domainEObj == null) {
            DomainEObj domain = this.getDomainBObj().queryEObjByShortDomain(shortDomain);
            //说明数据库也没有数据
            if (domain == null) {
                return null;
            }
            //使用异步线程放到缓存中去,遵循最近最少使用原则.
            submitTail(domain);
            return domain;
        } else {
            //高并发情况下 ,防止高并发,并将最近访问的更新到尾部
            submitHead(domainEObj);
            return domainEObj;
        }
}
}
