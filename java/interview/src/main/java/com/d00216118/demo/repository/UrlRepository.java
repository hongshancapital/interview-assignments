package com.d00216118.demo.repository;

import com.d00216118.demo.model.InfoUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Yu Chen
 * @email D00216118@student.dkit.ie
 * @date 1:12 下午 2021/4/2
 **/
@Repository
public interface UrlRepository extends JpaRepository<InfoUrl, Long> {

    InfoUrl findByUrl(String url);

    List<InfoUrl> findByMd5Url(String url);

    InfoUrl findByTinyUrlAndUserId(String tinyUrl,Long userId);

}
