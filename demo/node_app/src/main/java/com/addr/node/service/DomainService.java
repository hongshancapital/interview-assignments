package com.addr.node.service;

import java.util.Map;

public interface DomainService {

    int insertDomain(Map<String, String> map);

    String queryDomain(String shortDoamin);
}
