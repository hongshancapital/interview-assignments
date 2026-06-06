package com.example.shorturl.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface URLMappingRepository extends CrudRepository<URLMapping, Long> {

    URLMapping findByLurlHash(Long lurlHash);
}
