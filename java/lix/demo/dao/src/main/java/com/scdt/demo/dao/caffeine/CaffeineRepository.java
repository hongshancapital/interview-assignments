package com.scdt.demo.dao.caffeine;

import java.util.Optional;

public interface CaffeineRepository {

    void put(String key, Object value);

    <T> Optional<T> get(String key, Class<T> clazz);
}
