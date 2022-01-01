package url.converter.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import url.converter.common.constant.ErrorCode;
import url.converter.exception.UrlConverterException;
import url.converter.service.CheckService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Wang Siqi
 * @date 2021/12/31
 */
@Slf4j
@Service
public class CheckServiceImpl implements CheckService {

    private static final String URL_PREFIX_PATTERN = "https://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    @Override
    public void checkLongUrl(String longUrl) {
        formatCheck(longUrl);
    }

    @Override
    public void checkShortUrl(String shortUrl) {
        formatCheck(shortUrl);
    }

    private void formatCheck(String longUrl) {
        Pattern pattern = Pattern.compile(URL_PREFIX_PATTERN);
        Matcher matcher = pattern.matcher(longUrl);
        if (!matcher.matches()) {
            log.error("url不合法, longUrl={}", longUrl);
            throw new UrlConverterException(ErrorCode.URL_INVALID);
        }
    }
}
