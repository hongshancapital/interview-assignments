package com.example.shorturl.service.generator;

import com.example.shorturl.config.exception.BizException;
import com.example.shorturl.service.dto.GenerateEnum;
import com.example.shorturl.service.generator.genstrategy.IGeneratorStrategy;
import org.springframework.util.CollectionUtils;
import java.util.List;

/**
 * @author yyp
 * @date 2022/1/16 10:14
 */
public abstract class GenerateFactory {
    private List<IGeneratorStrategy> generatorStrategyList;
    private GenerateEnum generateEnum;

    public GenerateFactory(List<IGeneratorStrategy> generatorStrategyList, GenerateEnum generateEnum) {
        this.generatorStrategyList = generatorStrategyList;
        this.generateEnum = generateEnum;
    }

    /**
     * 获取生成器
     * @return
     */
    public IGeneratorStrategy getGenerator() {
        return chooseGenerator(generateEnum);
    }

    private IGeneratorStrategy chooseGenerator(GenerateEnum generateEnum) {
        if (CollectionUtils.isEmpty(generatorStrategyList)) {
            throw new BizException("未找到生成策略");
        }
        return generatorStrategyList.stream()
                .filter(x -> generateEnum.equals(x.support()))
                .findFirst()
                .orElseThrow(()->new BizException(String.format("未找到[%s]", generateEnum.getVal())));
    }
}
