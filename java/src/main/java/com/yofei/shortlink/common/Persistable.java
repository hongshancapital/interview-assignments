package com.yofei.shortlink.common;

import java.io.Serializable;
import java.util.function.Function;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Persistable implements Serializable, Comparable<Persistable> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id; // NOSONAR

    public boolean isNew() {
        return id == null;
    }

    @Override
    public boolean equals(final Object obj) {

        if (null == obj) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (!getClass().equals(obj.getClass())) {
            return false;
        }

        final Persistable that = (Persistable) obj;

        return null != this.getId() && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode += null == getId() ? 0 : getId().hashCode() * 31;
        return hashCode;
    }

    @Override
    public int compareTo(final Persistable o) {
        return Long.compare(this.id, o.id);
    }

    public static final Function<Persistable, Long> TO_IDENTITY = Persistable::getId;
}
