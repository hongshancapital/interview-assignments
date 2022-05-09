package com.wangxiao.shortlink.domain.shortlink;

import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class LinkPairRepositoryImpl implements LinkPairRepository {

    private ConcurrentHashMap<String, String> linkStorage = new ConcurrentHashMap();

    @Override
    public String saveIfAbsent(LinkPair linkPair) {
        return linkStorage.putIfAbsent(linkPair.getShortLink(), linkPair.getLongLink());
    }

    @Override
    public String getLongLink(String shortLink) {
        return linkStorage.get(shortLink);
    }

    @Override
    public Long totalPairSize() {
        return linkStorage.mappingCount();
    }
}
