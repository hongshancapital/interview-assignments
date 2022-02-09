package org.example.model;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import org.example.exception.CodeGeneratorException;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class CodeGeneratorWithHash {
    private static final Object obj = new Object();
    private static final char[] chars = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    private static final String SUFFIX = "_suffix";

    private static Set<Long> usedNumber = new ConcurrentSkipListSet<>();

    public String createNewCode(String longUrl) {
        synchronized (obj) {
            String url = longUrl;
            ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
            HashCode hashCode = Hashing.murmur3_32_fixed(threadLocalRandom.nextInt(0, url.length()))
                    .hashUnencodedChars(url);
            while (usedNumber.contains(hashCode.padToLong())) {
                url = url + SUFFIX;
                hashCode = Hashing.murmur3_32_fixed(threadLocalRandom.nextInt(0, url.length()))
                        .hashString(url, StandardCharsets.UTF_8);
            }

            long id = hashCode.padToLong();
            String newCode = to62String(id);
            usedNumber.add(id);
            return newCode;
        }
    }

    private String to62String(long num) {
        long cur = num;
        StringBuffer sb = new StringBuffer();
        while (cur > chars.length - 1) {
            sb.append(chars[Long.valueOf(cur % chars.length).intValue()]);
            cur = cur / chars.length;
        }

        sb.append(chars[Long.valueOf(cur).intValue()]);
        return sb.reverse().toString();
    }
}
