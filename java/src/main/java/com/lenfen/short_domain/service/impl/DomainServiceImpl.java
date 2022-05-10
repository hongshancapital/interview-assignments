package com.lenfen.short_domain.service.impl;

import com.lenfen.short_domain.bean.ShortDomain;
import com.lenfen.short_domain.exception.ShortDomainException;
import com.lenfen.short_domain.service.DomainService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 域名服务接口实现类
 */
@Service
public class DomainServiceImpl implements DomainService {

    /**
     * 短域名池最大容量
     */
    @Value("${domain-pool-size:3000}")
    private long poolSize;

    /**
     * 短域名池
     */
    private static final Map<String, ShortDomain> SHORT_DOMAIN_POOL = new HashMap<>(32);

    /**
     * 对长域名进行编码
     *
     * @return 短域名对象
     */
    @Override
    public ShortDomain encode(String fullUrl) {
        ShortDomain shortDomain = getAndCheckByFullUrl(fullUrl);
        if (shortDomain != null) {
            shortDomain.getEncodeCount().incrementAndGet();
            return shortDomain;
        }
        return createShortDomain(fullUrl);
    }

    /**
     * 创建一个短域名对象
     *
     * @param fullUrl 长域名
     * @return 短域名对象
     */
    private synchronized ShortDomain createShortDomain(String fullUrl) {
        ShortDomain shortDomain = getAndCheckByFullUrl(fullUrl);
        if (shortDomain != null) {
            return shortDomain;
        }

        if (SHORT_DOMAIN_POOL.size() >= poolSize) {
            throw new ShortDomainException("编码域名达到最大值");
        }

        String shortUrl = genShortUrl(fullUrl);
        ShortDomain newShortDomain = new ShortDomain();
        newShortDomain.setShortUrl(shortUrl);
        newShortDomain.setFullUrl(fullUrl);
        newShortDomain.setEncodeTime(LocalDateTime.now());
        newShortDomain.setEncodeCount(new AtomicLong(1));
        newShortDomain.setDecodeCount(new AtomicLong(0));

        SHORT_DOMAIN_POOL.put(shortUrl, newShortDomain);
        return newShortDomain;
    }

    /**
     * 获取并检查短域名信息
     * - 校验当前短域名信息是否为长域名对应的信息
     *
     * @param fullUrl 长域名
     * @return 短域名信息
     */
    private ShortDomain getAndCheckByFullUrl(String fullUrl) {
        String shortUrl = genShortUrl(fullUrl);
        ShortDomain shortDomain = SHORT_DOMAIN_POOL.get(shortUrl);
        if (shortDomain == null) {
            return null;
        }
        String originFullUrl = shortDomain.getFullUrl();
        if (!originFullUrl.equals(fullUrl)) {
            throw new ShortDomainException("域名编码冲突，冲突的域名为：" + originFullUrl);
        }
        return shortDomain;
    }

    /**
     * 对短域名进行解码
     *
     * @param shortUrl 短域名
     * @return 对于的短域名对象
     */
    @Override
    public ShortDomain decode(String shortUrl) {
        ShortDomain shortDomain = SHORT_DOMAIN_POOL.get(shortUrl);
        if (shortDomain != null) {
            shortDomain.getDecodeCount().incrementAndGet();
        }
        return shortDomain;
    }

    private final static char[] CHARS = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };

    private byte[] getInitIdx() {
        return new byte[]{'A', 'P', 'T', 'X', '4', '8', '6', '9'};
    }

    /**
     * 将字节码转换为字符串
     *
     * @param idx 字节码数组
     * @return 转换后的字符串
     */
    private String idxToString(byte[] idx) {
        char[] shortUrl = new char[idx.length];
        for (int i = 0; i < idx.length; i++) {
            shortUrl[i] = CHARS[(idx[i] & 0XFF) % CHARS.length];
        }
        return String.valueOf(shortUrl);
    }

    /**
     * 根据长域名生成短域名
     *
     * @param url 长域名
     * @return 短域名
     */
    public String genShortUrl(String url) {
        byte[] idx = getInitIdx();
        byte[] urlByte = url.getBytes();
        for (byte b : urlByte) {
            for (int i = 0; i < idx.length; i++) {
                if (i > 0) {
                    idx[i] = (byte) (b ^ idx[i] ^ idx[i - 1]);
                } else {
                    idx[i] = (byte) (b ^ idx[i]);
                }
            }
        }
        return idxToString(idx);
    }
}
