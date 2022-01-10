package com.liuxiang.service.impl;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.liuxiang.constant.GenerateTypeEnum;
import com.liuxiang.service.IGenerateId;
import org.springframework.stereotype.Service;

/**
 * @author liuxiang6
 * @date 2022-01-06
 **/
@Service
public class CrcGenerateService implements IGenerateId {

    @Override
    public String generateType() {
        return GenerateTypeEnum.CRC32.name();
    }

    @Override
    public String generate(String longUrl) {
        HashCode crcHashCode = Hashing.crc32().hashBytes(longUrl.getBytes());
        return crcHashCode.toString().substring(0, 8);
    }

}
