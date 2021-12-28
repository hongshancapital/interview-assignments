package com.scdt.shorturl.distributed.id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sequences {

    @Autowired
    private ZookeeperService client;

    public Long sequence(SequenceName sequenceName) {
        return this.client.sequence(sequenceName.name());
    }

    public enum SequenceName {
        SHORT_URL
    }

}