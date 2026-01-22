package com.scdt.url.tiny_url.representation;

import com.scdt.url.tiny_url.model.TinyUrl;
import org.springframework.stereotype.Component;

@Component
public class TinyUrlRepresentationService {

    public TinyUrlRepresentation toRepresentation(TinyUrl tinyUrl) {
        return new TinyUrlRepresentation(tinyUrl.getId().toString(), tinyUrl.getOriginalUrl(), tinyUrl.getCreatedAt());
    }
}
