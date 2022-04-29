package com.dblones.shortlink.util;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class SerialNumberUtils {

    private AtomicLong serialNumber = new AtomicLong(-1);

    public Long getSerialNumber(){
        return serialNumber.incrementAndGet();
    }

    public Long resetAndgetSerialNumber(){
        synchronized (serialNumber) {
            long newValue = serialNumber.get();
            if (newValue != -1) {
                serialNumber.set(-1);
            }
        }
        return serialNumber.incrementAndGet();
    }
}
