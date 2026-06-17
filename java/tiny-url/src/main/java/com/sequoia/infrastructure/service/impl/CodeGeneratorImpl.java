package com.sequoia.infrastructure.service.impl;

import com.sequoia.infrastructure.util.Constant;
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
@Component("codeGenerator")
public class CodeGeneratorImpl implements ICodeGenerator {

    @Resource(name = "murmurIdWorker")
    private IIdWorker idWorker;

    /**
     * 生成短码
     * @param originUrl 原生长链接
     * @return 转换后的短码
     */
    @Override
    public String generateTinyCode(String originUrl) {
        // 截取低位bit - 7位短码对应 42bit，8位短码对应 48bit
        long number = Constant.LONG_BITS_ALL_1 & idWorker.nextId(originUrl);
        // 将截取后bit的首位置1
        number = Constant.LONG_BITS_HEAD_1 | number;
        // 如果大于当前位62进制最大值，则bit集体右移1位
        number = (number > Constant.HEX62_MAX_LONG) ? number >> 1 : number;
        // 转换为62进制
        return HexUtil.getRandomHex62Char() + HexUtil.hex10To62(number);
    }

}
