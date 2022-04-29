package com.example.shorturl.service.core;

import com.example.shorturl.config.GeneratorConfig;
import com.example.shorturl.service.generator.GenerateFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author yyp
 * @date 2022/1/16 11:11
 */
@Service("randomShotUrlService")
public class RandomShotUrlService extends CommonShortUrlService {

    public RandomShotUrlService(@Qualifier("randomGenerateFactory") GenerateFactory generateFactory, GeneratorConfig config) {
        super(generateFactory, config);
    }
}
