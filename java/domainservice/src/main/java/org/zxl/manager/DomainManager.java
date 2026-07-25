package org.zxl.manager;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zxl.conf.DomainProperties;
import org.zxl.domain.Segment;
import org.zxl.proxy.StoreProxy;
import org.zxl.util.DomainUtils;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class DomainManager {

    private Segment segment;
    private AtomicInteger count = new AtomicInteger(0);
    @Autowired
    private DomainProperties domainProperties;
    @Autowired
    private StoreProxy storeProxy;

    @PostConstruct
    public void init() {
    }

    public void check() {
        int curCount = count.incrementAndGet();
        if (segment != null && curCount < domainProperties.getStep()) {
            return;
        }
        refresh(curCount);
    }

    private synchronized void refresh(int curCount) {
        int step = domainProperties.getStep();
        if (segment == null) {
            segment = storeProxy.loadSegment(step);
            return;
        }
        if (curCount == step) {
            segment = storeProxy.loadSegment(step);
        }
        if (curCount >= step) {
            count.set(0);
        }
        return;
    }

    public String convertToShortUrl(String url) {
        String recover = storeProxy.exits(url);
        if (StringUtils.isNotBlank(recover)) {
            return recover;
        }
        //检查是否需要重新加载分段
        check();
        String curBase62Number = DomainUtils.add(segment.getStart(), count.get());
        storeProxy.store(url, curBase62Number);
        return curBase62Number;
    }

    public String convertToLongUrl(String url) {
        return storeProxy.recover(url);
    }

}
