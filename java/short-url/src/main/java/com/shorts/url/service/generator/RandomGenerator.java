package com.shorts.url.service.generator;

import cn.hutool.core.util.RandomUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 随机方式 生成短链接
 * </p>
 *
 * @author WangYue
 * @date 2022/3/24 10:19
 */
@Component
public class RandomGenerator extends AbstractGenerator {

    @Value("${base.url}")
    private String baseUrl;

    @Override
    public String doGenerate(String longUrl) {
        return baseUrl + RandomUtil.randomString(8);
    }
}
