package com.example.shorturl.service.core;

import com.example.shorturl.config.GeneratorConfig;
import com.example.shorturl.service.generator.GenerateFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author yyp
 * @date 2022/1/16 11:11
 */
@Service("incrementShotUrlService")
public class IncrementShotUrlService extends CommonShortUrlService {

    IncrementShotUrlService(@Qualifier("incrementGenerateFactory") GenerateFactory factory, GeneratorConfig config) {
        super(factory, config);
    }
}
