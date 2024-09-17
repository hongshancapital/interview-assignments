package com.youyuzuo.shortdn.storage;

import com.youyuzuo.shortdn.dto.DomainNameDto;

public interface Storage {

    DomainNameDto save(DomainNameDto dto);

    DomainNameDto search(String shortDn);
}
