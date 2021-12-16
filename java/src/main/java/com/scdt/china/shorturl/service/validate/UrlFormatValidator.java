package com.scdt.china.shorturl.service.validate;

import com.scdt.china.shorturl.exception.BusinessException;
import com.scdt.china.shorturl.exception.BusinessExceptions;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * URL格式校验
 *
 * @author ng-life
 */
@Component
public class UrlFormatValidator implements UrlValidator {

    private static final Set<String> VALID_PROTOCOLS = new HashSet<>(Arrays.asList("http", "https"));

    @Override
    public void validate(String url) {
        try {
            URL result = new URL(url);
            String protocol = result.getProtocol();
            if (!VALID_PROTOCOLS.contains(protocol)) {
                throw new BusinessException(BusinessExceptions.UNSUPPORTED_PROTOCOL, "不支持的URL协议：" + protocol);
            }
        } catch (MalformedURLException e) {
            throw new BusinessException(BusinessExceptions.INVALID_URL, "URL格式不正确");
        }
    }

}
