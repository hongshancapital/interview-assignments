package com.hongshan.homework.persist;

import com.hongshan.homework.pojo.S2LURLMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface S2LURLMapperRepository extends JpaRepository<S2LURLMapper, String> {
    List<S2LURLMapper> findS2LURLMapperByLongURL(String longURL);
    List<S2LURLMapper> findS2LURLMapperByShortURL(String shortURL);
}
