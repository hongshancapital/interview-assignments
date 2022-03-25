package cn.sequoiacap.shorturl.service;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class HashGenerator implements ShortUrlGenerator {
    private static final HashFunction HASH_FUNCTION = Hashing.murmur3_32_fixed();

    // 短域名允许的所以字符的个数作为进制, 即 62 进制
    private static final char[] ELEMENTS = new char[] {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
            'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };

    private static final int RADIX = ELEMENTS.length;

    @Override
    public String generate(String originalUrl, int random) {
        HashFunction function = random == 0 ? HASH_FUNCTION : Hashing.murmur3_32_fixed(random);
        // hash 结果可能为负数, 转成 long 类型, 当做无符号数处理
        long hash = (0xFFFFFFFFL & function.hashString(originalUrl, StandardCharsets.UTF_8).asInt());
        // 转换为 RADIX 进制反转过来的数, 最后没有必要反转回来成为真正的 RADIX 进制的数, 只要统一规则即可
        StringBuilder sb = new StringBuilder(6);
        while (hash != 0) {
            sb.append(ELEMENTS[(int) (hash % RADIX)]);
            hash /= RADIX;
        }

        return sb.toString();
    }
}
