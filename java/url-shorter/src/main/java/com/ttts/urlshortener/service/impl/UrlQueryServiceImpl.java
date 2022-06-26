package com.ttts.urlshortener.service.impl;

import com.ttts.urlshortener.base.exception.BusinessException;
import com.ttts.urlshortener.base.util.NumberRadixUtils;
import com.ttts.urlshortener.domain.ExcelShortUrl;
import com.ttts.urlshortener.domain.ShortUrl;
import com.ttts.urlshortener.service.ShortUrlService;
import com.ttts.urlshortener.service.UrlQueryService;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UrlQueryServiceImpl implements UrlQueryService {

    private ShortUrlService shortUrlService;

    @Autowired
    public UrlQueryServiceImpl(ShortUrlService shortUrlService) {
      this.shortUrlService = shortUrlService;
    }

    @Override
    public String queryLurl(String surl) throws BusinessException {
        Long surlValue = NumberRadixUtils.sixtyTwoToDecimal(surl);
        ShortUrl shortUrl = shortUrlService.getBySurl(surlValue);
        if (shortUrl == null) {
            return null;
        }
        //检查是否过期，过期则删除
        Optional<ShortUrl> checked = checkExpired(shortUrl);
        if (checked.isPresent()) {
            return shortUrl.getLurl();
        } else {
            return null;
        }
    }

    @Override
    public Optional<ShortUrl> queryShortUrl(String lurl) {
        List<ShortUrl> records = shortUrlService.getByLurl(lurl);
        List<ShortUrl> checked = checkExpired(records);
        if (checked == null || checked.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(checked.get(0));
    }

    @Override
    public List<ExcelShortUrl> listAllExcelShortUrl() {
        List<ShortUrl> records = shortUrlService.listAllShortUrl();
        if (records == null || records.isEmpty()) {
            return Collections.emptyList();
        }
        List<ExcelShortUrl> result = records.stream()
            .map(it -> ExcelShortUrl.from(it))
            .collect(Collectors.toList());
        return result;
    }

    Optional<ShortUrl> checkExpired(ShortUrl shortUrl) {
        if (shortUrl == null || shortUrl.getExpiresTime() == null) {
            return Optional.empty();
        }
        LocalDateTime expiresTime = shortUrl.getExpiresTime();
        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(expiresTime)) {
            //过期，删除
            shortUrlService.deleteById(shortUrl.getId());
            return Optional.empty();
        } else {
            return Optional.of(shortUrl);
        }
    }

    List<ShortUrl> checkExpired(List<ShortUrl> records) {
        if (records == null || records.isEmpty()) {
            return Collections.emptyList();
        }
        List<ShortUrl> result = records.stream()
            .filter(it -> checkExpired(it).isPresent())
            .collect(Collectors.toList());
        return result;
    }
}
