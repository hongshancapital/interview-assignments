package com.web.work.validator;

import com.web.work.common.enums.HttpHeaderEnum;
import com.web.work.common.exception.BadRequestException;
import com.web.work.common.exception.ErrorCode;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Component;

/**
 * verify that the domain is legal
 *
 * @author chenze
 * @version 1.0
 * @date 2022/4/26 9:34 PM
 */
@Component
public class DomainValidator {

    public void validateUrl(String url) {
        String[] schemes = {HttpHeaderEnum.HTTP.getName(), HttpHeaderEnum.HTTPS.getName()};
        UrlValidator urlValidator = new UrlValidator(schemes);
        if (!urlValidator.isValid(url)) {
            throw new BadRequestException(ErrorCode.URL_EXCEPTION);
        }
    }
}
