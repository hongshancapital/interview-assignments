package com.example.shorturl.service;

import com.example.shorturl.config.WebProperties;
import com.example.shorturl.dao.URLMapping;
import com.example.shorturl.dao.URLMappingRepository;
import com.example.shorturl.vo.ShortURLNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class URLService {

    @Autowired
    WebProperties properties;
    @Autowired
    HashHelper helper;
    @Autowired
    URLMappingRepository repository;

    private final char CTRL_A = '\u0001';

    public String createShortURL(URLMapping urlMapping) {
        String lurl = urlMapping.getLurl();
        URLMapping umInDb = findOrInsert(lurl);
        Long hash = umInDb.getLurlHash();
        String base64URL = helper.convertToBase64URL(hash);
        return properties.getBase() + base64URL;
    }

    public String getLongURL(String surl) {
        Long lurlHash = helper.convertToLong(surl);
        URLMapping urlMapping = repository.findByLurlHash(lurlHash);
        if (urlMapping == null) {
            throw new ShortURLNotFoundException(surl);
        }
        String lurl = urlMapping.getLurl();
        return removeCtrlAIfHave(lurl);
    }

    private String removeCtrlAIfHave(String lurl) {
        Assert.notNull(lurl, "lurl must not be null");
        int len = lurl.length();
        int ctrlAIndex = -1;
        for (int i = len - 1; i >= 0; i--) {
            char c = lurl.charAt(i);
            if (c == CTRL_A) {
                ctrlAIndex = i;
            } else {
                break;
            }
        }
        // There is no CTRL_A
        if (ctrlAIndex < 0) {
            return lurl;
        }
        return lurl.substring(0, ctrlAIndex - 1);
    }

    private URLMapping findOrInsert(String lurl) {
        Long lurlHash = helper.hashTo48BitLong(lurl);
        URLMapping urlmInDb = repository.findByLurlHash(lurlHash);
        if (urlmInDb == null) {
            // insert new record
            return insert(lurl, lurlHash);
        } else {
            String existedLurl = urlmInDb.getLurl();
            String lrulNoCtrlA = removeCtrlAIfHave(lurl);
            boolean isSameLurl = lrulNoCtrlA.equals(removeCtrlAIfHave(existedLurl));
            if (isSameLurl) {
                // already existed in db
                return urlmInDb;
            } else {
                // handle hash collision
                return findOrInsert(lurl + CTRL_A);
            }
        }
    }

    private URLMapping insert(String lurl, Long lurlHash) {
        URLMapping um = new URLMapping();
        um.setLurl(lurl);
        um.setLurlHash(lurlHash);
        return repository.save(um);
    }
}
