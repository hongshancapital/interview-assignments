package com.example.shorturl.service.generator;

import com.example.shorturl.service.dto.GenerateEnum;
import com.example.shorturl.service.generator.genstrategy.IGeneratorStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yyp
 * @date 2022/1/16 10:55
 */
@Component("incrementGenerateFactory")
public class IncrementGenerateFactory extends GenerateFactory {

    public IncrementGenerateFactory(List<IGeneratorStrategy> generatorStrategyList) {
        super(generatorStrategyList, GenerateEnum.INCREMENT_GENERATOR);
    }
}
