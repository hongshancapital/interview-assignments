package com.scdt.china.shorturl.service.validate;

import com.scdt.china.shorturl.exception.BusinessException;
import com.scdt.china.shorturl.exception.BusinessExceptions;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * URL长度校验
 *
 * @author ng-life
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UrlLengthValidator implements UrlValidator {

    /**
     * 最大URL长度限制
     */
    private static final int MAX_URL_LENGTH_LIMIT = 2048;

    @Override
    public void validate(String url) {
        if (url == null || url.isEmpty()) {
            throw new BusinessException(BusinessExceptions.INVALID_URL, "URL为空");
        }
        if (url.length() > MAX_URL_LENGTH_LIMIT) {
            throw new BusinessException(BusinessExceptions.INVALID_URL, "URL过长");
        }
    }

}
