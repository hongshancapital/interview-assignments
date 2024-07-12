package com.example.shorturl.service;

import com.example.shorturl.vo.ShortURLIllegalArgumentException;
import com.google.common.base.Strings;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.regex.Pattern;

@Component
public class HashHelper {
    /**
     * hash seed
     */
    private final int SEED = 2006090100;
    /**
     * 2^48 which hexadecimal is ffffffffffff
     */
    private Long bits48 = 281474976710655L;
    /**
     * The base64URL length is 8
     */
    private final int base64Len = 8;
    /**
     * use UTF-8 Charset to hash String
     */
    private final Charset UTF8 = Charset.forName("UTF-8");

    /**
     * 128 bits MururHash3
     */
    private final HashFunction hashFunc = Hashing.murmur3_128(SEED);

    /**
     * sorted base64Url mapping dictionary
     */
    private char[] base64UrlDict = new char[]{
            '-',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z',
            '_',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z'
    };

    /**
     * base64UrlDict size
     */
    private int SIZE = base64UrlDict.length;

    /**
     * Regexp for base64Url
     */
    private Pattern pattern = Pattern.compile("^[A-Za-z0-9\\-_]{8}$");

    public Long hashTo48BitLong(String input) {
        Assert.notNull(input, "Input can't be null for hash");
        HashCode hashCode = hashFunc.hashString(input, UTF8);
        Long hashValue = hashCode.asLong();
        Long hash48Bits = hashValue & bits48;
        return hash48Bits;
    }

    /**
     * convert Long to base64URL
     *
     * @param num
     * @return base64URL String
     */
    public String convertToBase64URL(Long num) {
        longArgumentCheck(num);
        StringBuffer sb = new StringBuffer();
        char zero = base64UrlDict[0];
        // corner case for 0
        if (num.longValue() == 0L) {
            for (int i = 0; i < base64Len; i++) {
                sb.append(zero);
            }
            return sb.toString();
        }
        while (num > 0) {
            int i = (int) (num % SIZE);
            sb.append(base64UrlDict[i]);
            num /= SIZE;
        }
        // left padding for zero -
        int sbLen = sb.length();
        if (sbLen < base64Len) {
            for (int i = 0; i < base64Len - sbLen; i++) {
                sb.append(zero);
            }
        }
        return sb.reverse().toString();
    }

    /**
     * Convert base64URl String to Long number
     *
     * @param base64URL
     * @return Long number
     */
    public Long convertToLong(String base64URL) {
        base64URLArgumentCheck(base64URL);
        long result = 0;
        char[] charArray = base64URL.toCharArray();
        int length = charArray.length;
        for (char c : charArray) {
            length--;
            int position = Arrays.binarySearch(base64UrlDict, c);
            result += (long) Math.pow(SIZE, length) * position;
        }
        return result;
    }

    private void base64URLArgumentCheck(String base64URL) throws ShortURLIllegalArgumentException {
        if (Strings.isNullOrEmpty(base64URL)) {
            throw new ShortURLIllegalArgumentException("短链接字符码不能为空");
        }
        boolean isBase64Find = pattern.matcher(base64URL).find();
        if (!isBase64Find) {
            throw new ShortURLIllegalArgumentException("短链接字符码只能由字母、数字、中划线、下划线组成，长度是8个字符。");
        }
    }

    private void longArgumentCheck(Long num) throws IllegalArgumentException {
        if (num == null) {
            throw new IllegalArgumentException("convertToBase64URL input num is null");
        }
        if (num.longValue() < 0 || num.longValue() > bits48) {
            String msg = String.format("convertToBase64URL Long num should be >=0 and <=%s, input is %s", bits48, num);
            throw new IllegalArgumentException(msg);
        }
    }
}
