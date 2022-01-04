package me.huchao.sd.service.impl;

import me.huchao.sd.domain.url.manager.BucketManager;
import me.huchao.sd.domain.url.model.Node;
import me.huchao.sd.service.ShortDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author huchao
 */
@Service
public class ShortDomainServiceImpl implements ShortDomainService {

    private BucketManager bucketManager;

    public ShortDomainServiceImpl(@Autowired BucketManager bucketManager) {
        super();
        this.bucketManager = bucketManager;
    }

    @Override
    public Node getByShortDomain(String shortDomain) {
        if (null == shortDomain || shortDomain.isEmpty()) {
            throw new IllegalArgumentException("shortDomain can not be empty");
        }
        return this.bucketManager.getByCode(shortDomain);
    }

    @Override
    public Node getByOrigin(String origin) throws Exception {
        if (null == origin || origin.isEmpty()) {
            throw new IllegalArgumentException("origin can not be empty");
        }
        return this.bucketManager.getByOrigin(origin);
    }
}
