package com.sequoiacap.tinyurl.service.impl;

import com.google.common.base.Charsets;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.sequoiacap.tinyurl.exception.BadParamException;
import com.sequoiacap.tinyurl.service.IdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import static com.sequoiacap.tinyurl.utils.Base62Util.isValidBaseChar;
import static com.sequoiacap.tinyurl.utils.Base62Util.toBase;

/**
 * 采用murmur哈希方式生成短码
 */
@Service
public class HashIdGenerator implements IdGenerator {
    private static final int MAX_ID_LEN = 8;

    @Override
    public String nextId(String url) {
        HashFunction hf = Hashing.murmur3_128();
        long hashcode = hf.newHasher()
                .putString(url, Charsets.UTF_8)
                .hash().padToLong();
        return toBase(hashcode, MAX_ID_LEN);
    }

    @Override
    public void checkId(String id) throws BadParamException {
        // id不能为空，且id长度为MAX_ID_LEN字符，字符均位于BASE_CHARS字符串中
        if (StringUtils.isEmpty(id) || id.length() != MAX_ID_LEN) {
            throw new BadParamException();
        }
        for (int i = 0; i < id.length(); ++i) {
            if (!isValidBaseChar(id.charAt(i))) {
                throw new BadParamException();
            }
        }
    }


}
