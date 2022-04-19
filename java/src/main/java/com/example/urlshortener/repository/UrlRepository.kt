package com.example.urlshortener.repository

import com.example.urlshortener.model.ShortUrl
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

/**
 * @author  lilu - Li Lu (7939743@qq.com)
 * @version 1.0
 * @since   2022/04/19
 */
@Repository
@Transactional
interface UrlRepository : JpaRepository<ShortUrl, String>