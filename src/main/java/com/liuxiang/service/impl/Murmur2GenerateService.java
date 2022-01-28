package com.liuxiang.service.impl;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.liuxiang.constant.GenerateTypeEnum;
import com.liuxiang.service.IGenerateId;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;

/**
 * @author liuxiang6
 * @date 2022-01-06
 **/
@Service
public class Murmur2GenerateService implements IGenerateId {

    @Override
    public String generateType() {
        return GenerateTypeEnum.MUR_MUR.name();
    }

    @Override
    public String generate(String longUrl) {
        HashFunction hashFunction = Hashing.murmur3_32();
        HashCode murmurHashCode = hashFunction.hashString(longUrl, Charset.forName("utf-8"));
        return murmurHashCode.toString().substring(0, 8);
    }

}
