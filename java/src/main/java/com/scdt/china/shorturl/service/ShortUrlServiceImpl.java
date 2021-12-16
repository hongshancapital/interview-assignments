package com.scdt.china.shorturl.service;

import com.scdt.china.shorturl.exception.BusinessException;
import com.scdt.china.shorturl.exception.BusinessExceptions;
import com.scdt.china.shorturl.repository.UrlRepository;
import com.scdt.china.shorturl.service.validate.UrlValidator;
import com.scdt.china.shorturl.utils.NumberCodecUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * URL服务
 *
 * @author ng-life
 */
@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    /**
     * 最大短码长度：8
     */
    private static final int MAX_CODE_LENGTH = 8;
    private final UrlRepository urlRepository;
    private final List<UrlValidator> validators;

    public ShortUrlServiceImpl(UrlRepository urlRepository,
                               List<UrlValidator> validators) {
        this.urlRepository = urlRepository;
        this.validators = validators;
    }

    @Override
    public String generate(String url) {
        for (UrlValidator validator : validators) {
            validator.validate(url);
        }
        Long urlId = urlRepository.save(url);
        return NumberCodecUtils.encode(urlId);
    }

    @Override
    public String expand(String shortCode) {
        if (ObjectUtils.isEmpty(shortCode) || shortCode.length() > MAX_CODE_LENGTH) {
            throw new BusinessException(BusinessExceptions.INVALID_CODE, "编码长度无效");
        }
        Long urlId;
        try {
            urlId = NumberCodecUtils.decode(shortCode);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(BusinessExceptions.INVALID_CODE);
        }
        return urlRepository.get(urlId);
    }
}
