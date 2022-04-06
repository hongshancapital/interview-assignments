package org.goofly.shortdomain.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.goofly.shortdomain.repository.ShortDomainRepository;
import org.goofly.shortdomain.service.GeneratorService;
import org.goofly.shortdomain.service.ShortDomainService;
import org.goofly.shortdomain.utils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ShortDomainServiceImpl implements ShortDomainService {

    @Autowired
    @Qualifier("localGenerator")
    private GeneratorService generatorService;

    @Autowired
    private ShortDomainRepository shortDomainRepository;

    @Override
    public String convertShortDomain(String url) {

        if (StringUtils.isEmpty(url)) {
            throw new IllegalArgumentException("param [url] is not allowed to be empty");
        }

        //生成号码
        Long generateCode = generatorService.generateCode();
        Objects.requireNonNull(generateCode,"generateCode resource exhausted.");

        //转换短域名
        String shortCode = ConvertUtils.encode(generateCode);

        //存储映射关系
        shortDomainRepository.save(shortCode,url);

        return shortCode;
    }

    @Override
    public String convertOriginalDomain(String shortCode) {
        return shortDomainRepository.getOriginalUrl(shortCode);
    }
}
