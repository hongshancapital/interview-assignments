package com.scdt.url.tiny_url;

import com.scdt.url.tiny_url.model.TinyUrl;
import com.scdt.url.tiny_url.model.TinyUrlId;
import com.scdt.url.tiny_url.model.TinyUrlStorages;
import org.springframework.stereotype.Repository;

@Repository
public class TinyUrlRepository {

    private final TinyUrlStorages tinyUrlStorages;

    public TinyUrlRepository(TinyUrlStorages tinyUrlStorages) {
        this.tinyUrlStorages = tinyUrlStorages;
    }

    public void save(TinyUrl tinyUrl) {
        tinyUrlStorages.putIfAbsent(tinyUrl.getId().getId(), tinyUrl);
    }

    public TinyUrl byId(TinyUrlId id) {
        var tinyUrl = tinyUrlStorages.get(id.toString());
        if (tinyUrl == null) {
            throw new TinyUrlNotFoundException(id);
        }
        return tinyUrl;
    }

    public TinyUrl byOriginalUrl(String originalUrl) {
        return tinyUrlStorages.getSupplemental(originalUrl);
    }
}
