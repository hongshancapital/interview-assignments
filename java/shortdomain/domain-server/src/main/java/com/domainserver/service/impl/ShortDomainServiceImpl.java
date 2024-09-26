package com.domainserver.service.impl;

import com.domaincore.constants.BusinessConstants;
import com.domaincore.exceptions.BusinessException;
import com.domaincore.util.IdHashUtil;
import com.domaincore.util.SnowflakeIdWorker;
import com.domainserver.handle.AutoUnlockProxy;
import com.domainserver.service.IShortDomainService;
import com.domainserver.util.DomainValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 短域名服务实现类
 *
 * @author 905393944@qq.com
 * @Date 2021/9/21
 */
@Service(value = "shortDomainServiceImpl")
public class ShortDomainServiceImpl implements IShortDomainService {

    static IdHashUtil idHashUtil = new IdHashUtil("shorturl", BusinessConstants.SHORTURL_MAX_LENGTH);


    /**
     * 长域名为key短域名为value
     */
    static Map<String, String> shortDomainDataMap = new ConcurrentHashMap<>(BusinessConstants.MAXIMUM_CAPACITY);

    /**
     * 短域名为key长域名为value
     */
    static Map<String, String> longDomainDataMap = new ConcurrentHashMap<>(BusinessConstants.MAXIMUM_CAPACITY);

    /**
     * 功能描述: 通过长域名获取短域名
     *
     * @param longDomain 长域名
     * @return java.lang.String
     * @throws Exception
     * @author 905393944@qq.com
     */
    @Override
    public String getShortDomain(String longDomain) throws BusinessException {
        DomainValidator.validateLongUrl(longDomain);
        try (AutoUnlockProxy autoUnlockProxy = new AutoUnlockProxy(new ReentrantLock())) {
            autoUnlockProxy.lock();
            String shortDomain = shortDomainDataMap.get(longDomain);
            // 计算短域名前先从map中获取，取不到再重新计算短域名
            if (StringUtils.isNotBlank(shortDomain)) {
                return shortDomain;
            }
            // 雪花算法获取全局唯一id，再将id做进制转换后获取字符
            SnowflakeIdWorker snowFlake = new SnowflakeIdWorker(BusinessConstants.WORKERID, BusinessConstants.DATACENTERID);
            shortDomain = BusinessConstants.BASE_URL + idHashUtil.encode(snowFlake.nextId());
            if (shortDomainDataMap.size() >= BusinessConstants.MAXIMUM_CAPACITY) {
                shortDomainDataMap.clear();
                longDomainDataMap.clear();
            }
            shortDomainDataMap.putIfAbsent(longDomain, shortDomain);
            longDomainDataMap.putIfAbsent(shortDomain, longDomain);
            return shortDomain;
        } catch (BusinessException exp) {
            throw new BusinessException(exp);
        }
    }

    /**
     * 功能描述: 通过短域名获取长域名
     *
     * @param shortDomain 短域名
     * @return java.lang.String
     * @author 905393944@qq.com
     * @Date 2021/9/21
     */
    @Override
    public String getLongDomain(String shortDomain) {
        DomainValidator.validateLongUrl(shortDomain);
        return longDomainDataMap.getOrDefault(shortDomain, "长域名不存在");
    }
}
