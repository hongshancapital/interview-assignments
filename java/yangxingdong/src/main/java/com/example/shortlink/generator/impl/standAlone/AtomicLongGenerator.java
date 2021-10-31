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


    /**
     * the offset we use ,when application restarted / redeployed.
     */
    private static  Long OFFSET = null ; // here we can wrap the null
                                              // maybe use NULL Object.


    private static final AtomicLong SEQUENCER = new AtomicLong(OFFSET) ;




    @Value("${generator.offset.enabled:false}")
    private boolean offsetEnabled;


    @PostConstruct
    private void setOffset(){
        OFFSET = offsetEnabled ? loadOffset() : 0L;
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
