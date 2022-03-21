package com.sequoiacap.shortlinkcenter.common.domain;

import java.io.Serializable;

/**
 * 领域实体对象
 */
public interface Entity<T> extends Serializable {

    /**
     * Returns the identifier of this entity instance.
     *
     * @return the entity identifier.
     */
    T getId();
}
