package com.scdt.url.tiny_url;

import com.scdt.url.common.ddd.ApplicationService;
import com.scdt.url.tiny_url.model.TinyUrlFactory;
import com.scdt.url.tiny_url.model.TinyUrlId;
import com.scdt.url.tiny_url.representation.TinyUrlRepresentation;
import com.scdt.url.tiny_url.representation.TinyUrlRepresentationService;
import org.springframework.stereotype.Component;

@Component
public class TinyUrlApplicationService implements ApplicationService {

    //region variables
    private final TinyUrlRepository repository;
    private final TinyUrlFactory factory;
    private final TinyUrlRepresentationService representationService;

    public TinyUrlApplicationService(TinyUrlRepository repository, TinyUrlFactory factory, TinyUrlRepresentationService representationService) {
        this.repository = repository;
        this.factory = factory;
        this.representationService = representationService;
    }
    //endregion

    public TinyUrlId create(CreateTinyUrlCommand command) {
        var tinyUrl = repository.byOriginalUrl(command.getOriginalUrl());
        if (tinyUrl == null) {
            tinyUrl = factory.create(command.getOriginalUrl());
            repository.save(tinyUrl);
        }
        return tinyUrl.getId();
    }

    public TinyUrlRepresentation byId(String id) {
        var tinyUrl = repository.byId(tinyUrlId(id));
        return representationService.toRepresentation(tinyUrl);
    }

    private TinyUrlId tinyUrlId(String id) {
        return TinyUrlId.tinyUrlId(id);
    }
}
