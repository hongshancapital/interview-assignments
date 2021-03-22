package com.demo.urlshortlink.repository;

import com.demo.urlshortlink.domain.UrlLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
  jap 持久化
 */
@Repository
public interface UrlLinkRepository extends JpaRepository<UrlLink,Long> {

}
