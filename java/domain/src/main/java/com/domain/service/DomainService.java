package com.domain.service;

import com.domain.bean.DomainConfigBean;
import com.domain.bean.DomainValueBean;
import com.domain.exception.DomainException;
import com.domain.exception.ExceptionEnums;
import com.domain.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ：ji
 * @date ：2021/11/19
 * @description：
 */
@Service
public class DomainService {
    private final static Logger logger = LoggerFactory.getLogger(DomainService.class);
    /**
     * 存储URL映射
     */
    private static ConcurrentHashMap<String,DomainValueBean> domainMap = new ConcurrentHashMap();
    /**
     * 7个递增数值
     */
    private static AtomicInteger s1 = new AtomicInteger(1);
    private static AtomicInteger s2 = new AtomicInteger(5);
    private static AtomicInteger s3 = new AtomicInteger(7);
    private static AtomicInteger s4 = new AtomicInteger(15);
    private static AtomicInteger s5 = new AtomicInteger(17);
    private static AtomicInteger s6 = new AtomicInteger(29);
    private static AtomicInteger s7 = new AtomicInteger(31);
    private final static int URL_LEN = 8;

    @Autowired
    private DomainConfigBean domainConfigBean;

    /**
     * 提供获取 domainMap的方法
     * @return
     */
    public ConcurrentHashMap<String,DomainValueBean> getDomainMap(){
        return domainMap;
    }

    /**
     * 通过短链接获取原URL
     * 为空则返回空值，调用方法需要进行null 判断
     * @param domainKey
     * @return
     */
    public String getUrl(String domainKey){
        DomainValueBean valueBean = domainMap.get(domainKey);
        // 为空则返回null
        if (valueBean == null){
            return null;
        }
        // 返回url
        return valueBean.getUrl();
    }

    /**
     * 通过shortUrl获取 domainKey（长短链接映射关系key值）
     * @param shortUrl
     * @return
     */
    public String getDomainKey(String shortUrl){
        String domainKey = shortUrl.replace(domainConfigBean.getServerUrl(),"");
        if (domainKey.length() != URL_LEN){
            logger.error("getDomainKey 短链接非法shortUrl={}",shortUrl);
            throw new DomainException(ExceptionEnums.PARAM_SHORT_URL_ERROR);
        }
        return domainKey;
    }

    /**
     * 拼接短域名服务地址
     *
     * @param domainKey
     * @return
     */
    public String buildDomainInternetUrl(String domainKey){
        return domainConfigBean.getServerUrl().concat(domainKey);
    }

    /**
     * 获取8位映射标识符，并放入缓存
     * 1、第1位根据系统配置，
     * 2、后7位通过随机数，每次通过随机数来判定那个值进行递增，每次递增一个值
     *
     * @param url
     * @return
     */
    public String shortName(String url) {
        // 递增随机数
        int[] result = incrementAndGetIndex();
        // 获取字符集
        StringBuilder domainKey = new StringBuilder();
        for (int index : result){
            domainKey.append(chars[index % 62]);
        }
        // 存储
        domainMap.put(domainKey.toString(),new DomainValueBean(url,System.currentTimeMillis()));
        logger.info("shortName 添加url映射domainKey={},url={}",domainKey,url);
        return domainKey.toString();
    }

    /**
     * 对7位随机数进行递增
     */
    private int[] incrementAndGetIndex(){
        int[] result = new int[]{
                domainConfigBean.getMachineId(),
                s1.get(),
                s2.get(),
                s3.get(),
                s4.get(),
                s5.get(),
                s6.get(),
                s7.get()
        };
        // 增加可读性，这里随机数进行+1
        int random = CommonUtil.getSecureRandom(URL_LEN) + 1;
        switch (random){
            case 1:
                result[1] = s1.incrementAndGet();
                break;
            case 2:
                result[2] = s2.incrementAndGet();
                break;
            case 3:
                result[3] = s3.incrementAndGet();
                break;
            case 4:
                result[4] = s4.incrementAndGet();
                break;
            case 5:
                result[5] = s5.incrementAndGet();
                break;
            case 6:
                result[6] = s6.incrementAndGet();
                break;
            default:
                result[7] = s7.incrementAndGet();
        }
        return result;
    }

    /**
     * 字符集
     */
    private static String[] chars = new String[]{
            "M","c","N","z","O","g","S","T",
            "q","F","r","u","D","v","w","x",
            "a","d","Y","e","P","f","R","h",
            "6","B","7","A","8","9","C","s",
            "i","l","J","m","k","n","o","p",
            "E","H","t","I","G","j","K","L",
            "y","1","Q","2","X","3","4","5",
            "U","0","V","b","W","Z"
    };
}
