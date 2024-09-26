package com.example.shorturl.service.generator.genstrategy;

import com.example.shorturl.service.dto.GenerateEnum;

/**
 * @author yyp
 * @date 2022/1/16 10:09
 */
public interface IGeneratorStrategy {
    /**
     * 生成方式
     * @return
     */
    GenerateEnum support();

    /**
     * 生成的号码
     * @return
     */
    String generateCode();
}
