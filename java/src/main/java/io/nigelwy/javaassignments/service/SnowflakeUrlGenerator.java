package io.nigelwy.javaassignments.service;

import io.nigelwy.javaassignments.ShortUrlProperties;
import io.nigelwy.javaassignments.util.Snowflake;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SnowflakeUrlGenerator implements UrlGenerator {

    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    private final Snowflake snowflake;

    private final ShortUrlProperties properties;

    @Override
    public String generateShortUrl(String originUrl) {
        var id = snowflake.getId() - Snowflake.MIN_VALUE;
        return convertToString(id);
    }

    private String convertToString(long number) {
        var seed = DIGITS.length;
        var maxLength = properties.getMaxLength();
        var sb = new StringBuilder();
        while ((number / seed) > 0) {
            sb.append(DIGITS[(int) (number % seed)]);
            number /= seed;
        }
        sb.append(DIGITS[(int) (number % seed)]);
        if (sb.length() > maxLength) {
            throw new IllegalStateException();
        }
        return sb.toString();
    }
}
