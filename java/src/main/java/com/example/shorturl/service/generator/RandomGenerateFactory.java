package com.example.shorturl.service.generator;

import com.example.shorturl.service.dto.GenerateEnum;
import com.example.shorturl.service.generator.genstrategy.IGeneratorStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yyp
 * @date 2022/1/16 10:55
 */
@Component("randomGenerateFactory")
public class RandomGenerateFactory extends GenerateFactory {

    public RandomGenerateFactory(List<IGeneratorStrategy> generatorStrategyList) {
        super(generatorStrategyList, GenerateEnum.RANDOM_GENERATOR);
    }
}
