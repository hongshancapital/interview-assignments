package org.domain.service;

import org.domain.domain.Link;
import org.domain.exception.InvalidParamException;
import org.domain.mapper.LinkMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Service
public class LinkService {
    @Resource
    private LinkMapper linkMapper;

    public Link getById(int id) {
        if (id < 1) {
            throw new InvalidParamException("invalid id");
        }
        return linkMapper.selectLinkById(id);
    }

    public Link getByUrl(String url) {
        if (StringUtils.isEmpty(url)) {
            throw new InvalidParamException("invalid url");
        }
        return linkMapper.selectLinkByUrl(url);
    }

    public int insertLink(String url) {
        if (StringUtils.isEmpty(url)) {
            throw new InvalidParamException("invalid url");
        }
        Link link = new Link();
        link.setUrl(url);
        return linkMapper.insertLink(link);
    }


}
