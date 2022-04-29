package com.my.linkapi.service;

import com.my.linkapi.dto.LinkShortRequestDto;
import org.springframework.stereotype.Component;

public interface LinkLengthConversionService {
    String toShort(LinkShortRequestDto linkShortRequestDto);

    String toShort(String link);

    String toLong(String link);
}
