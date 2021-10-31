package com.example.shortlink.galting;


import com.example.shortlink.generator.GlobalIdGenerator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * the Galting (AKA galting gun) , here we use it as a firing gun to get a "ticket num" for encode
 * short link with {@code Generator}. and we also provider an SPI mechanism to let them choose which
 * Generator to use , we add this feature in the future version.
 */
@Component
public class Galting {


    @Resource(name = "atomicLongGenerator")
    private GlobalIdGenerator<Long> generator;


    /**
     * send a number to the service
     * @return
     */
    public Long shootNum(){
        return generator.getId();
    }




}
