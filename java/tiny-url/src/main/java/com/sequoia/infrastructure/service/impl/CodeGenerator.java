package com.sequoia.infrastructure.service.impl;

import com.sequoia.infrastructure.util.HexUtil;
import com.sequoia.service.ICodeGenerator;
import com.sequoia.service.IIdWorker;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * CodeGenerator
 *
 * @author KVLT
 * @date 2022-03-30.
 */
@Component
public class CodeGenerator implements ICodeGenerator {

    @Resource
    private IIdWorker sfIdWorker;

    /**
     * 雪花算法 生成
     *  第一位为校验位 + 七位62进制数
     * @param originUrl
     * @return
     */
    @Override
    public String generateTinyCode(String originUrl) {
        // 控制在 40 位，对应的 62进制数在7位
        long number = 0xffffffffffL & sfIdWorker.nextId(originUrl);
        String originCode = HexUtil.hex10To62(number);
        // 第一位设置为 校验位
        return HexUtil.getCheckCode(originCode) + originCode;
    }

}
