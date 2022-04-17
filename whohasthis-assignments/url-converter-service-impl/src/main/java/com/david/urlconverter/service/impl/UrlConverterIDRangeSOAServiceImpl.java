package com.david.urlconverter.service.impl;

import com.david.urlconverter.model.web.IdRange;
import com.david.urlconverter.service.dubbo.IUrlConverterIDRangeSOAService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 *
 * @author whohasthis
 */
@Service
@DubboService(version = "${urlConverter.service.version}")
@Slf4j
public class UrlConverterIDRangeSOAServiceImpl implements IUrlConverterIDRangeSOAService {

    private static final Long START_ID = 1000L;

    //private static final Long MAX_ID = 218340105584895L;

    //for junit test use small MAX_ID
    private static final Long MAX_ID = 100000L;

    @Value("${id.range.step}")
    private int stepRange;

    @PostConstruct
    public void init(){
        currentStartId = START_ID;
    }

    private volatile Long currentStartId;

    @Override
    public IdRange getNextAvailableRange() {
        IdRange idRange = null;
        synchronized (this) {
            Long startId = currentStartId;
            Long endId = startId + stepRange;
            if(endId > MAX_ID){
                //reinit currentStartId;
                //log.info("reinit current start id");
                System.out.println("reinit current start id");
                currentStartId = START_ID;
                startId = currentStartId;
            }
            setCurrentStartId(startId + stepRange);
            idRange = new IdRange(startId, startId + stepRange);
        }
        return idRange;
    }

    private synchronized void setCurrentStartId(Long value){
        currentStartId = value;
    }

}

