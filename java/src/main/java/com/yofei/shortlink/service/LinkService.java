package com.yofei.shortlink.service;

import com.yofei.shortlink.dao.entity.LinkEntity;
import com.yofei.shortlink.dao.repository.LinkRepository;
import com.yofei.shortlink.utils.IdUtils;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class LinkService {

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private CacheService cacheService;

    public String create(String url) {
        String md5 = IdUtils.md5(url);

        LinkEntity linkEntity = linkRepository.findByMd5Equals(md5);

        if (linkEntity == null) {
            linkEntity = new LinkEntity();
            linkEntity.setMd5(md5);
            linkEntity.setUrl(url);
            linkEntity = linkRepository.save(linkEntity);
        }

        String code = IdUtils.toCode(linkEntity.getId());
        if (code.length()<8) {

        }
        cacheService.setUrl(code,url);
        return code;
    }

    public String getUrl(String code) {
        String url = cacheService.getUrl(code);

        if (!StringUtils.isEmpty(url)) {
            return url;
        }

        long id = IdUtils.toID(code);

        Optional<LinkEntity> linkOptional = linkRepository.findById(id);

        if (linkOptional.isPresent()) {
            cacheService.setUrl(code,linkOptional.get().getUrl());
            return linkOptional.get().getUrl();
        }

        return null;
    }
}
