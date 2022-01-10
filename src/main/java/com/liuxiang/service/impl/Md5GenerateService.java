package com.liuxiang.service.impl;

import com.liuxiang.constant.GenerateTypeEnum;
import com.liuxiang.service.IGenerateId;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * @author liuxiang6
 * @date 2022-01-06
 **/
@Service
public class Md5GenerateService implements IGenerateId {

    @Override
    public String generateType() {
        return GenerateTypeEnum.MD5.name();
    }

    @Override
    public String generate(String longUrl) {
        String md5Id = DigestUtils.md5DigestAsHex(longUrl.getBytes());
        return md5Id.substring(0, 8);
    }
}
