package com.zz.generator;

import com.zz.exception.BusinessException;

/**
 * 短链接生成器
 *
 * @author zz
 * @version 1.0
 * @date 2022/3/31
 */
public interface ShortUrlGenerator {
    String convertToCode(String url) throws BusinessException;
    boolean isValid(String shortCode);
}
