package com.youyuzuo.shortdn.service;

import com.youyuzuo.shortdn.dto.DomainNameDto;


public interface DomainNameService {

    DomainNameDto save(String longDn);

    DomainNameDto queryLongDn(String shortDn);
}
