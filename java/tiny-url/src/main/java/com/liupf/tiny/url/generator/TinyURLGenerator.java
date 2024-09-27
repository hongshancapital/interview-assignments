package com.liupf.tiny.url.generator;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.liupf.tiny.url.domain.TinyURL;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;

/**
 * 短链接构造
 */
@Component
public class TinyURLGenerator {

    @Value("${tiny-url.service.path}")
    private String baseURL;

    @Resource
    private CodeGenerator codeGenerator;

    public TinyURL build(String longUrl) {
        String code = codeGenerator.nextCode();
        return new TinyURL(code, longUrl);
    }

    public String genShortUrl(TinyURL tinyURL) {
        return baseURL + tinyURL.getCode();
    }

    /**
     * 从URL中解析Code，
     */
    public String parseCode(String shortUrl) {
        String path = URLUtil.getPath(shortUrl);
        return StrUtil.sub(path, path.length() - 8, path.length());
    }

}
