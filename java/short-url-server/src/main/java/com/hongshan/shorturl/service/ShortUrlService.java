package com.hongshan.shorturl.service;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.hongshan.shorturl.config.Constants;
import com.hongshan.shorturl.config.RegisterContext;
import com.hongshan.shorturl.model.exception.NotAllowedException;
import com.hongshan.shorturl.model.exception.NotFoundException;
import com.hongshan.shorturl.model.exception.ServerErrorException;
import com.hongshan.shorturl.model.exception.UrlExpireException;
import com.hongshan.shorturl.model.reqs.ShortUrlGenRequest;
import com.hongshan.shorturl.model.resps.ShortUrlResp;
import com.hongshan.shorturl.model.url.HostRegister;
import com.hongshan.shorturl.model.url.ShortUrl;
import com.hongshan.shorturl.util.CuratorUtil;
import com.hongshan.shorturl.util.LongConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: huachengqiang
 * @date: 2022/1/15
 * @description:
 */
@Service
public class ShortUrlService {
    private static final Logger logger = LoggerFactory.getLogger(ShortUrlService.class);
    private static final String incr_prefix = "incr:prefix:%d";
    private static final Map<String, ShortUrl> urlMap = new ConcurrentHashMap<>();
    private String incr_key;
    private long workId = -1;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private CuratorUtil curatorUtil;

    /**
     * 初始化workId
     *
     * @throws Exception
     */
    @PostConstruct
    public void initIncrKey() throws Exception {
        for (int i = 0; i < Constants.STEPS; i++) {
            String nodePath = Constants.SUB_NODES_PATH_PREFIX + i;
            if (!curatorUtil.exists(nodePath)) {
                try {
                    String node = curatorUtil.createNode(nodePath);
                    if (node.equals(nodePath)) {
                        workId = i;
                        break;
                    }
                } catch (Exception e) {
                    logger.warn("create node fail, nodePath={}", nodePath, e);
                }
            }
        }
        if (workId < 0) {
            throw new RuntimeException("init workId error");
        }
        incr_key = String.format(incr_prefix, workId);
    }

    /**
     * 生成短链接
     *
     * @param request
     * @return
     */
    public ShortUrlResp genShortUrl(ShortUrlGenRequest request) {
//        checkUrl(request);
        Long increment = redisTemplate.opsForValue().increment(incr_key, 1);
        if (Objects.isNull(increment)) {
            throw new ServerErrorException("服务异常");
        }
        String suffix = LongConvertUtil.convert(increment + workId * Constants.GAP + Constants.OFFSET);
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setShortUrl(Constants.PREFIX + suffix);
        shortUrl.setOriginUrl(request.getOriginUrl());
        if (Objects.nonNull(request.getExpireAt())) {
            shortUrl.setExpireAt(request.getExpireAt());
        }
        urlMap.put(suffix, shortUrl);
        ShortUrlResp resp = new ShortUrlResp();
        BeanUtils.copyProperties(shortUrl, resp);
        return resp;
    }

    /**
     * 原始域名的url的域名需要先注册，注册完获取到计算签名的secret，根据secret对原始url签名，
     *
     * @param request
     */
    private void checkUrl(ShortUrlGenRequest request) {
        String host = UriComponentsBuilder.fromHttpUrl(request.getOriginUrl()).build().getHost();
        HostRegister register = RegisterContext.find(host);
        if (Objects.isNull(register) || register.getExpire().before(new Date())) {
            throw new NotFoundException("未授信域名，请先注册");
        }
        String sign = Hashing.md5().newHasher().putString(register.getSecret() + "-" + request.getOriginUrl(), Charsets.UTF_8).hash().toString();
        if (!sign.equals(request.getSign())) {
            throw new NotAllowedException("数据非法！");
        }
    }

    /**
     * 根据短链接的后缀获取原始链接，超时则删除内存中的key
     *
     * @param suffix
     * @return
     */
    public String getShorUrl(String suffix) {
        ShortUrl shortUrl = urlMap.get(suffix);
        if (Objects.isNull(shortUrl)) {
            throw new NotFoundException("无效链接");
        }
        if (Objects.nonNull(shortUrl.getExpireAt()) && shortUrl.getExpireAt() < System.currentTimeMillis() / 1000) {
            urlMap.remove(suffix);
            throw new UrlExpireException("链接已经失效");
        }
        return shortUrl.getOriginUrl();
    }
}
