package org.example.model;

import org.example.exception.CodeGeneratorException;
import org.example.exception.UrlConvertorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class UrlConvertor {
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlConvertor.class.getName());

    @Autowired
    @Qualifier("hashIds")
    private CodeGenerator codeGenerator;

    @Value("${host}")
    private String host;

    public String createShortUrl(String longUrl) throws UrlConvertorException {

        // 曾经生成过短域名，直接取出shortcode拼接返回
        String shortCode = UrlCache.getShortCode(longUrl);
        if(!StringUtils.isEmpty(shortCode)) {
            return host + shortCode;
        }

        try {
            shortCode = codeGenerator.createNewCode();
        } catch (CodeGeneratorException e) {
            LOGGER.error(e.getMessage(), e);
            throw new UrlConvertorException("create short url failed");
        }

        // 维护映射关系
        UrlCache.add(shortCode, longUrl);

        return host + shortCode;
    }

    public String getLongUrlByShortCode(String shortCode) {
        return UrlCache.getLongUrl(shortCode);
    }
}
