package org.example.model;

import org.example.exception.CodeGeneratorException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ThreadLocalRandom;

@Component("autoId")
public class CodeGeneratorWithAutoId implements CodeGenerator {
    private static final Object obj = new Object();
    private static final char[] chars = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    private static Set<Long> usedNumber = new ConcurrentSkipListSet<>();

    private static long startNumber = 1L;

    private static long endNumber = 218340105584895L;

    private static final int digit = 62;

    @Override
    public String createNewCode() throws CodeGeneratorException {
        synchronized (obj) {
            long cur = getRandomNumber();
            StringBuffer sb = new StringBuffer();
            while (cur > digit - 1) {
                sb.append(chars[Long.valueOf(cur % digit).intValue()]);
                cur = cur / digit;
            }

            sb.append(chars[Long.valueOf(cur).intValue()]);
            return sb.reverse().toString();
        }
    }

    private long getRandomNumber() throws CodeGeneratorException {
        if (usedNumber.size() == (endNumber - startNumber + 1)) {
            throw new CodeGeneratorException("no ID can be used");
        }

        long l = 1;
        do {
            l = ThreadLocalRandom.current().nextLong(startNumber, endNumber + 1);
        } while (usedNumber.contains(l));
        usedNumber.add(l);
        return l;
    }
}
