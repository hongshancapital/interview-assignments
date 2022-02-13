package com.liupf.tiny.url.generator;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.liupf.tiny.url.utils.IdCreator;
import com.liupf.tiny.url.utils.Code62Util;

/**
 * 8位Code生成器
 */
@Component
public class CodeGenerator {

    @Resource
    private IdCreator idCreator;

    /**
     * 生成下一个Code
     */
    public String nextCode() {
        long id = idCreator.nextId();
        return Code62Util.convertTo64(id);
    }

    /**
     * 按业务ID生成Code
     */
    public String nextCode(int bizId) {
        long id = idCreator.nextId(bizId);
        return Code62Util.convertTo64(id);
    }

}
