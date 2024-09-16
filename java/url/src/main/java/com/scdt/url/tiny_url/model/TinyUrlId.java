package com.scdt.url.tiny_url.model;

import com.scdt.url.common.ddd.Identity;
import com.scdt.url.common.ddd.ValueObject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TinyUrlId implements Identity, ValueObject {

    private String id;

    public static TinyUrlId tinyUrlId(String id) {
        return new TinyUrlId(id);
    }


    @Override
    public String toString() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TinyUrlId tinyUrlId = (TinyUrlId) o;
        return Objects.equals(id, tinyUrlId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
