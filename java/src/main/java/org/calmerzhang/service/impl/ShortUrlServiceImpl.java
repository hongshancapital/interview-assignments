package org.calmerzhang.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.calmerzhang.common.constant.RetConstant;
import org.calmerzhang.repo.api.ShortUrlRepo;
import org.calmerzhang.repo.model.ShortUrlMappingPO;
import org.calmerzhang.service.api.ShortUrlService;
import org.calmerzhang.common.exception.BusinessException;
import org.calmerzhang.util.RadixUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 短链服务实现
 *
 * @author calmerZhang
 * @create 2022/01/10 10:41 上午
 */
@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    @Autowired
    private ShortUrlRepo shortUrlRepo;

    private static int MAX_INCREMENT = 10;
    private static Random random = new Random();
    private static AtomicLong codeNum = new AtomicLong();

    @Override
    public String getShortUrl(String longUrl) throws BusinessException {
        ShortUrlMappingPO existShortUrl = shortUrlRepo.getByLongUrl(longUrl);
        if (null != existShortUrl && StringUtils.isNotBlank(existShortUrl.getShortUrl())) {
            return existShortUrl.getShortUrl();
        }

        return generateShortUrl(longUrl);
    }

    /**
     * 生成短域名
     * @param longUrl
     * @return
     */
    private String generateShortUrl(String longUrl) throws BusinessException {
        long codeNum = getCodeNum();
        String domain = shortUrlRepo.getDomain();
        if (StringUtils.isBlank(domain)) {
            throw new BusinessException(RetConstant.BUSINESS_FAIL, RetConstant.NO_DOMAIN_MSG);
        }
        String shortUrl = domain + RadixUtil.get62String(codeNum);
        ShortUrlMappingPO shortUrlMappingPO = ShortUrlMappingPO.builder().shortUrl(shortUrl).longUrl(longUrl).build();
        shortUrlRepo.saveLongShortUrl(shortUrlMappingPO);

        return shortUrl;
    }

    /**
     * 获取code数值
     * @return
     */
    private long getCodeNum() {
        return codeNum.accumulateAndGet(random.nextInt(MAX_INCREMENT), ((left, right) -> left + right));
    }

    @Override
    public String getLongUrl(String shortUrl) {
        return Optional.ofNullable(shortUrlRepo.getByShortUrl(shortUrl)).map(ShortUrlMappingPO::getLongUrl).orElse(StringUtils.EMPTY);
    }
}
