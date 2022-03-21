package com.scdtchina.service.impl;

import com.scdtchina.common.NumberGenerator;
import com.scdtchina.config.SystemConfig;
import com.scdtchina.service.IShortDomainNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static com.scdtchina.common.ResultCode.PARAM_IS_INVALID;
import static com.scdtchina.common.ResultCode.SYSTEM_CAPACITY_FULL;

/**
 * @author fhzz
 * 进制转换短域名生成服务类
 */
@Service
public class CarryConversionDnServcieImpl implements IShortDomainNameService {

    @Autowired
    private SystemConfig systemConfig;

    private AtomicLong keyGen = new AtomicLong(0);

    ConcurrentHashMap<Long,String> storageMap = new ConcurrentHashMap<Long,String >();

    @Override
    public String generateShortDomainName(String longDomainName) {

        if(StringUtils.isEmpty(longDomainName)) {return PARAM_IS_INVALID.message;}

        long key = keyGen.incrementAndGet();

        if(key>systemConfig.getMaxKey()) {return SYSTEM_CAPACITY_FULL.message;}

        String code = NumberGenerator.getInstance().encode(key,62);

        storageMap.put(key,longDomainName);

        return code;
    }

    @Override
    public String obtainLongDomainName(String shortDomainName) {

        if(StringUtils.isEmpty(shortDomainName))  {return PARAM_IS_INVALID.message;}

        if(shortDomainName.length()>systemConfig.getCodeLength() ) { return SYSTEM_CAPACITY_FULL.message; }

        Long key =  NumberGenerator.getInstance().decode(shortDomainName,62);

        return storageMap.get(key);
    }

}
