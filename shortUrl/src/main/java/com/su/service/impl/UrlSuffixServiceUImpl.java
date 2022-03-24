package com.su.service.impl;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.su.service.IUrlSuffixService;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * @author hejian
 */
@Service
public class UrlSuffixServiceUImpl implements IUrlSuffixService {

    @Override
    public String getSuffix(String url) {
        HashCode hashCode = Hashing.murmur3_32().hashString(url, StandardCharsets.UTF_8);
        return hashCode.toString();
    }
}
