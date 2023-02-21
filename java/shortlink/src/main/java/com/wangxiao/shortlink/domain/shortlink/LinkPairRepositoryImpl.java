package com.wangxiao.shortlink.domain.shortlink;

import com.wangxiao.shortlink.infrastructure.common.PersistenceException;
import com.wangxiao.shortlink.infrastructure.persisitence.Persistence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Slf4j
public class LinkPairRepositoryImpl implements LinkPairRepository {

    private final ConcurrentHashMap<String, String> linkStorage = new ConcurrentHashMap<>();

    @Resource
    private Persistence persistence;

    @Override
    public String saveIfAbsent(LinkPair linkPair) {
        String saveResult = linkStorage.putIfAbsent(linkPair.getShortLink(), linkPair.getLongLink());
        //持久化数据
        if (saveResult == null) {
            try {
                persistence.persist(linkPair.getShortLink(), linkPair.getLongLink());
            } catch (Exception e) {
                removeLink(linkPair.getShortLink());
                log.error("持久化异常！", e);
                throw new PersistenceException();
            }
        }
        return saveResult;
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

    @Override
    public void clear() {
        linkStorage.clear();
    }
}
