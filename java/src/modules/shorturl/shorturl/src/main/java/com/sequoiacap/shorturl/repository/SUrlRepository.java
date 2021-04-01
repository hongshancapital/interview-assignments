package com.sequoiacap.shorturl.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sequoiacap.shorturl.model.SUrl;

@Repository
public interface SUrlRepository
    extends JpaRepository<SUrl, Long>, JpaSpecificationExecutor<SUrl>
{
	@Query("SELECT COUNT(s) FROM SUrl s WHERE s.shortUrl = ?1 AND s.status = ?2")
	public int countShorturl(String shortUrl, SUrl.Status status);
	
	@Query("SELECT s FROM SUrl s WHERE s.shortUrl = ?1 AND s.status = ?2")
	public List<SUrl> findShorturl(String shortUrl, SUrl.Status status, Pageable page);
	
	@Modifying
	@Query("UPDATE SUrl s SET s.status = ?4 WHERE s.type = ?1 AND s.status = ?2 AND s.timestamp <= ?3")
	public int refreshStatus(
		SUrl.Type type, SUrl.Status oldStatus,
		Timestamp timestamp, SUrl.Status newStatus);
}
