package me.huchao.sd.service;

import me.huchao.sd.domain.url.model.Node;

public interface ShortDomainService {
    Node getByShortDomain(String shortDomain) ;

    Node getByOrigin(String origin) throws Exception ;
}
