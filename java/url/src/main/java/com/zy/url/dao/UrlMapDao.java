package com.zy.url.dao;

import com.zy.url.entity.UrlMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlMapDao extends JpaRepository<UrlMap, Long> {
}
