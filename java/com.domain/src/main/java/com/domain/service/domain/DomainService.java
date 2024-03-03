package com.domain.service.domain;

import com.domain.bo.DomainBO;
import com.domain.config.GlobalParametersConfig;
import com.domain.po.StorePO;
import com.domain.service.stroe.StoreService;
import com.domain.utils.ShortUrlGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 短域名服务
 * @author jacky
 * @version 1.0
 * @since 1.0
 */
@Service
public class DomainService {

    @Autowired
    private StoreService storeService;  //存储服务

    @Autowired
    private GlobalParametersConfig globalParametersConfig;  //全局配置

    private static final int LOCK_TIMEOUT=10;  //10秒
    /**
     * 生成短地址编码
     * @param url  长地址
     * @return  String 短地址编码
     */
    private String createAddressCode(String url){
        ReentrantLock lock=null;
        try {
            lock = new ReentrantLock(true);  //公平锁
            lock.tryLock(LOCK_TIMEOUT, TimeUnit.SECONDS);
            return ShortUrlGenerator.generator(url);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
           if(lock!=null&&lock.isLocked())lock.unlock();
        }
        return null;
    }
    /**
     * 创建短地址
     * @param domainBO  请求busi bo
     * @return  domainBO  isSuccess 成功
     */
    public DomainBO createShortDomain(DomainBO domainBO){
       if(StringUtils.isEmpty(domainBO.getLongUrl())){
           domainBO.setIsSuccess(false);
           domainBO.setMsg("长域名不可为空");
           return domainBO;
       }
        String code=createAddressCode(domainBO.getLongUrl());
       if(StringUtils.isEmpty(code)){
           domainBO.setIsSuccess(false);
           return domainBO;
       }
        StorePO storePO =new StorePO(); //存储po
        storePO.setLongUrl(domainBO.getLongUrl());
        storePO.setAddressCode(code);
        storePO.setShortUrl(globalParametersConfig.getUrl()+storePO.getAddressCode());
        if(!storeService.check(storePO)){  //并发防止code重复  概率小
            StorePO saveStorePO=storeService.get(code);
            if(saveStorePO.getLongUrl().equals(storePO.getLongUrl())){
                domainBO.setIsSuccess(true);
                domainBO.setAddressCode(saveStorePO.getAddressCode());
                domainBO.setShortUrl(saveStorePO.getShortUrl());
                return domainBO;
            }
        }
        storeService.save(storePO); //存储
        domainBO.setIsSuccess(true);
        domainBO.setAddressCode(storePO.getAddressCode());
        domainBO.setShortUrl(storePO.getShortUrl());
        return domainBO;
    }
    /**
     *  根据短地址获取长地址
     * @param domainBO  请求busi bo
     * @return  domainBO  isSuccess 成功
     */
    public DomainBO getLongDomain(DomainBO domainBO){
        if(StringUtils.isEmpty(domainBO.getAddressCode())&&
                StringUtils.isEmpty(domainBO.getShortUrl())){
            domainBO.setIsSuccess(false);
            domainBO.setMsg("短域名不可为空");
            return domainBO;
        }
        String code=domainBO.getAddressCode();  //优先根据传入的code
        if(StringUtils.isEmpty(code)){
            //其次根据 短地址 去域名化
            code=domainBO.getShortUrl().replace(globalParametersConfig.getUrl(),"");
        }
        StorePO storePO=storeService.get(code);
        if(storePO==null){
            domainBO.setIsSuccess(false);
            return domainBO;
        }
        BeanUtils.copyProperties(storePO,domainBO);
        domainBO.setIsSuccess(true);
        return domainBO;
    }
}
