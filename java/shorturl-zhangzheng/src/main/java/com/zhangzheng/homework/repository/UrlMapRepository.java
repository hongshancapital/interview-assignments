package com.zhangzheng.homework.repository;

import com.zhangzheng.homework.entity.UrlMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhangzheng
 * @version 1.0
 * @description: TODO
 * @date 2021/10/9 下午1:58
 */
@Repository
public interface UrlMapRepository extends JpaRepository<UrlMap,Long> {
        List<UrlMap> getResultByShortUrl(String shortUrl);
        List<UrlMap> getResultByLongUrl(String longUrl);
}
