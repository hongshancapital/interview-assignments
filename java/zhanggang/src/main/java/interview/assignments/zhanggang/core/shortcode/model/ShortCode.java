package interview.assignments.zhanggang.core.shortcode.model;

import interview.assignments.zhanggang.config.exception.ShortCodeMaximumLimitException;
import interview.assignments.zhanggang.config.properties.ShortCodeProperties;
import interview.assignments.zhanggang.support.Base62Util;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ShortCode {
    private final long seed;
    private final ShortCodeProperties properties;

    public String getValue() {
        final String code = Base62Util.base10to62(seed);
        if (code.length() > properties.getMaxLength()) {
            throw new ShortCodeMaximumLimitException(seed);
        }
        return code;
    }
}
