package com.wangxiao.shortlink.domain.shortlink;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class LinkPairRepositoryImpl implements LinkPairRepository {


    private final ConcurrentHashMap<String, String> linkStorage = new ConcurrentHashMap<>();

    @Override
    public String saveIfAbsent(LinkPair linkPair) {
        return linkStorage.putIfAbsent(linkPair.getShortLink(), linkPair.getLongLink());
    }

    @Override
    public String getLongLink(String shortLink) {
        return linkStorage.get(shortLink);
    }

    @Override
    public String removeLink(String shortLink) {
        return linkStorage.remove(shortLink);
    }

    @Override
    public Long totalPairSize() {
        return linkStorage.mappingCount();
    }

    @Override
    public void load(Map<String, String> load) {
        linkStorage.putAll(load);
    }
}
