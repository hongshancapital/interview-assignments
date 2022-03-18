package com.yofei.shortlink.dao.repository;

import com.yofei.shortlink.dao.entity.LinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends JpaRepository<LinkEntity,Long>, JpaSpecificationExecutor<LinkEntity> {

    LinkEntity findByMd5Equals(String md5);
}
