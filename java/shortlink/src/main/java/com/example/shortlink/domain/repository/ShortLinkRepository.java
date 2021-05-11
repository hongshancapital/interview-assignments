package com.example.shortlink.domain.repository;

import com.example.shortlink.domain.entity.ShortLink;

public interface ShortLinkRepository {

    void saveShortLink(ShortLink shortLink);

    String getSourceLinkByShortLinkKey(String url);

}