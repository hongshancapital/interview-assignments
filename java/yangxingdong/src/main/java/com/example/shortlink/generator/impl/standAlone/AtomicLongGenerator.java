package com.example.shortlink.generator.impl.standAlone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicLong;


/**
 * this generator should take the question into consideration :
 *
 *    -- Application restart / redeployed , in this case, we lost
 *       the sequencer index now , so we have to make a storage to DB
 *       or middleware.
 *
 *
 *    -- but now , this question supposed we only use JVM Memory ,so
 *       this version we don't fix it .
 *
 */
@Component
public class AtomicLongGenerator extends StandAloneGenerator<Long> {




    private static AtomicLong SEQUENCER = new AtomicLong(0L) ;




    @Value("${generator.offset.enabled:false}")
    private boolean offsetEnabled;


    @PostConstruct
    private void setOffset(){
        SEQUENCER = offsetEnabled ? new AtomicLong(loadOffset()) : new AtomicLong(0L);
    }



    /**
     * load the offset from db or middleware.
     */
    private  Long loadOffset(){

        Long offset = 0L ;
        try {
            // TODO , here we can get the offset from db or middleaware
        } catch (Exception e) {
            throw new RuntimeException("please check the OFFSET.");
        }


        return offset;
    }



    @Override
    public Long getId() {
        // use cas
        return SEQUENCER.incrementAndGet();
    }
}
