package org.goofly.shortdomain.config;

import org.goofly.shortdomain.service.GeneratorService;
import org.goofly.shortdomain.service.impl.DBGeneratorServiceImpl;
import org.goofly.shortdomain.service.impl.LocalGeneratorServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : goofly
 * @Email: 709233178@qq.com
 */
@Configuration
public class GeneratorConfiguration {

    @Bean
    @ConditionalOnExpression("'${generator.selector}'.equals('local')")
    public GeneratorService localGenerator(){
        return new LocalGeneratorServiceImpl();
    }

    @Bean
    @ConditionalOnExpression("'${generator.selector}'.equals('db')")
    public GeneratorService dbGenerator(){
        return new DBGeneratorServiceImpl();
    }
}
