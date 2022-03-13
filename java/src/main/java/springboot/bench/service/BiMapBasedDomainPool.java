package springboot.bench.service;

import java.util.concurrent.locks.ReentrantLock;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import springboot.bench.consts.Constants;
import springboot.bench.utils.RandomStrGenerator;

public class BiMapBasedDomainPool implements DomainServiceIntf {

    private static volatile DomainServiceIntf theInstance = null;

    private BiMap<String, String> domainPoolMap;
    
    private int counter;

    private ReentrantLock lock;

    private BiMapBasedDomainPool() {
        domainPoolMap = HashBiMap.create(Constants.INITIAL_CAPACITY);
        
        counter = 0;
        lock = new ReentrantLock();
    }

    public static DomainServiceIntf get() {
        if (theInstance == null) {
            synchronized (BiMapBasedDomainPool.class) {
                if (theInstance == null) {
                    theInstance = new BiMapBasedDomainPool();
                }
            }
        }

        return theInstance;
    }
    
    @Override
    public String saveLongDomain(String longDomain) throws Exception {
        lock.lock();
        String result = null;

        try {
            if (domainPoolMap.containsKey(longDomain)) {
                throw new Exception(Constants.ERR_LONG_DOMAIN_EXISTS);
            }

            if (counter >= Constants.MAX_CAPACITY) {
                throw new Exception(Constants.ERR_DOMAIN_POOL_FULL);
            }
            
//            BiMap<String, String> shortToLongMap = domainPoolMap.inverse();

            while (true) {
                String str = RandomStrGenerator.genShortDomain();
//                if (shortToLongMap.containsKey(str)) {
//                    continue;
//                }
                
                domainPoolMap.put(longDomain, str);
                counter++;
                result = str;
                
                break;
            }

        } finally {
            lock.unlock();
        }

        return result;
    }

    @Override
    public String getOriginalDomain(String shortDomain) {
        lock.lock();

        String result = null;
        try {
            result = domainPoolMap.inverse().get(shortDomain);
        } finally {
            lock.unlock();
        }

        return result;
    }

}
