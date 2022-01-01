package url.converter.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import url.converter.common.constant.ErrorCode;
import url.converter.dto.ShortLongUrlDto;
import url.converter.exception.BadRequestParamException;
import url.converter.exception.UrlConverterException;
import url.converter.facade.vo.LongUrlGetReq;
import url.converter.facade.vo.LongUrlGetRes;
import url.converter.facade.vo.ShortUrlAddReq;
import url.converter.facade.vo.ShortUrlAddRes;
import url.converter.service.CheckService;
import url.converter.service.UrlConverterService;
import url.converter.util.Base62Util;

import javax.annotation.Resource;
import java.net.URI;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Wang Siqi
 * @date 2021/12/31
 */
@Slf4j
@Service
public class UrlConverterServiceImpl implements UrlConverterService {

    @Resource
    private CheckService checkService;

    private volatile static ConcurrentHashMap<String, ShortLongUrlDto> shortLongMap = new ConcurrentHashMap<>();
    private static final int MAX_SIZE = 10000; // shortLongMap 最大存储size

    private volatile static AtomicLong id = new AtomicLong(1);
    private static final long MAX_ID = 3521614606207L; // 7位62进制最大值 3521614606207L

    @Override
    public ShortUrlAddRes addShortUrl(ShortUrlAddReq req) {
        checkService.checkLongUrl(req.getLongUrl());

        // jvm 内存存储上限
        if (shortLongMap.size() > MAX_SIZE) {
            log.error("没有足够的空间, req={}", req);
            throw new UrlConverterException(ErrorCode.NO_MEM_USABLE);
        }

        long uniqId = id.getAndIncrement();
        // 内存存储上限已有限制, 此处不再额外判断
        /*if (uniqId > MAX_ID) {
            log.error("短链个数到达上限, req={}", req);
            throw new UrlConverterException(ErrorCode.SHORT_URL_UP_LIMIT);
        }*/

        String uniqStr = Base62Util.encodeWithDefault(uniqId);
        String longUrl = req.getLongUrl();
        String shortUrl = splice(getDomain(longUrl), uniqStr);

        shortLongMap.put(shortUrl, ShortLongUrlDto.builder()
                .shortUrl(shortUrl)
                .longUrl(longUrl)
                .build());

        return ShortUrlAddRes.builder().shortUrl(shortUrl).build();
    }

    @Override
    public LongUrlGetRes getLongUrl(LongUrlGetReq req) {
        checkService.checkLongUrl(req.getShortUrl());

        ShortLongUrlDto dto = shortLongMap.get(req.getShortUrl());
        if (Objects.isNull(dto)) {
            log.warn("url不合法, req={}", req);
            throw new BadRequestParamException(ErrorCode.URL_INVALID);
        }
        return LongUrlGetRes.builder()
                .longUrl(dto.getLongUrl())
                .build();
    }

    private String getDomain(String url) {
        try {
            URI uri = new URI(url);
            return uri.getHost();
        } catch (Exception e) {
            log.error("url处理异常, {}", url);
            throw new UrlConverterException(ErrorCode.URL_HANDLE_EXCEPTION);
        }
    }

    private String splice(String domain, String uniqStr) {
        return "https://".concat(domain.concat("/")).concat(uniqStr);
    }
}
