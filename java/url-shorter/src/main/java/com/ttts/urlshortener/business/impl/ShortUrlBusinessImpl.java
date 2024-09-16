package com.ttts.urlshortener.business.impl;

import com.ttts.urlshortener.business.ShortUrlBusiness;
import com.ttts.urlshortener.domain.ShortUrl;
import com.ttts.urlshortener.repository.ShortUrlRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShortUrlBusinessImpl implements ShortUrlBusiness {

    private ShortUrlRepository shortUrlRepository;

    @Autowired
    public ShortUrlBusinessImpl(ShortUrlRepository shortUrlRepository) {
      this.shortUrlRepository = shortUrlRepository;
    }

    @Override
    public int add(ShortUrl value) {
        return shortUrlRepository.add(value);
    }

    @Override
    public int deleteById(Long id) {
        return shortUrlRepository.deleteById(id);
    }

    @Override
    public ShortUrl getBySurl(Long surl) {
        return shortUrlRepository.getBySurl(surl);
    }

    @Override
    public List<ShortUrl> getByLurl(String lurl) {
        return shortUrlRepository.getByLurl(lurl);
    }

    @Override
    public List<ShortUrl> listAllShortUrl() {
        return shortUrlRepository.listAllShortUrl();
    }
}
