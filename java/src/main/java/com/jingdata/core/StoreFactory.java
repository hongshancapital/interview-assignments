package com.jingdata.core;

import com.jingdata.exception.BizException;
import com.jingdata.exception.ExceptionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 存储工厂
 *
 * @Author
 * @Date
 */
@Component
public class StoreFactory {

    @Value("${jingdata.strategy}")
    private String strategy;

    @Autowired
    ApplicationContext applicationContext;

    public Store getStore() throws BizException {
        Map<String, Store> beans = applicationContext.getBeansOfType(Store.class);
        for(Store bean : beans.values()) {
            if(bean.getStrategyName().equals(strategy)) {
                return bean;
            }
        }
        throw new BizException(ExceptionCode.UNSUPPORT);
    }

}
