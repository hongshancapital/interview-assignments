package me.huchao.sd.domain.url.manager;

import me.huchao.sd.domain.DomainException;
import me.huchao.sd.domain.url.model.Bucket;
import me.huchao.sd.domain.url.model.Node;

/**
 * @author huchao36
 */
public class BucketManager {

    private Bucket bucket;

    public BucketManager(Bucket bucket) {
        super();
        if (null == bucket) {
            throw new IllegalArgumentException("bucket of bucket manager can not be null");
        }
        this.bucket = bucket;
    }

    public Node getByCode(String code) {
        if (null == code || code.isEmpty()) {
            throw new IllegalArgumentException("code can not be empty");
        }
        return this.bucket.getByCode(code);
    }

    public Node getByOrigin(String origin) throws DomainException {
        if (null == origin || origin.isEmpty()) {
            throw new IllegalArgumentException("origin can not be empty");
        }
        return this.bucket.getByOrigin(origin);
    }
}
