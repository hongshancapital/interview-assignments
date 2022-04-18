package com.scdt.java.shortLink.component.util;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class ShortLinkUtil {

    @Resource
    private UrlCacheUtil urlCacheUtil;

    private static final int urlLength = 6;

    static final char[] CHARS =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
                    'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
                    'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
                    'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public String generateShortLink(String md5Hex, int i) {
        String subString = StringUtils.substring(md5Hex, i * 8, (i + 1) * 8);
        int binaryLength = urlLength * 6;
        long binaryLengthFixer = Long.valueOf(StringUtils.repeat("1", binaryLength), 2);
        subString = Long.toBinaryString(Long.valueOf(subString, 16) & binaryLengthFixer);
        subString = StringUtils.leftPad(subString, binaryLength, "0");
        StringBuilder sbBuilder = new StringBuilder();
        for (int j = 0; j < urlLength; j++) {
            String subString2 = StringUtils.substring(subString, j * 6, (j + 1) * 6);
            int charIndex = Integer.valueOf(subString2, 2) & 61;
            sbBuilder.append(CHARS[charIndex]);
        }
        return sbBuilder.toString();
    }

    public boolean existShortLink(String shortLink) {
        return !urlCacheUtil.getLongFromShort(shortLink).equals("");
    }
}
