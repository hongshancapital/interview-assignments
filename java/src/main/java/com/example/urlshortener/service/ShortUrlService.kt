package com.example.urlshortener.service

import com.example.urlshortener.model.ShortUrl
import com.example.urlshortener.repository.UrlRepository
import org.apache.commons.validator.routines.UrlValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.security.InvalidParameterException
import com.google.common.hash.Hashing;

/**
 * @author  lilu - Li Lu (7939743@qq.com)
 * @version 1.0
 * @since   2022/04/19
 */
@Service
class ShortUrlService(
    @param:Autowired private val repository: UrlRepository
) {

    companion object {
        const val ALPHABETS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        const val BASE = ALPHABETS.length
        const val MAX_LEN = 8
    }

    private val validator: UrlValidator = UrlValidator()

    /**
     * Shorten a URL and save it to the database
     * @param url
     * @return shorted URL id
     */
    fun save(url: String): String {
        if (!validator.isValid(url)) throw InvalidParameterException("The url is invalid!")
        val shortUrl = ShortUrl().apply {
            id = shortenUrl(url)
            originalUrl = url
        }
        repository.save(shortUrl)
        return shortUrl.id!!
    }

    /**
     * Find a shorted Url by id
     * @param id
     * @return original Url
     */
    fun getOriginalUrl(id: String): String? {
        val shortUrl = repository.findById(id).get()
//        repository.save(shortUrl)
        return shortUrl.originalUrl
    }

}

fun shortenUrl(url: String): String {
    val murmur32 = Hashing.murmur3_32_fixed().hashString(url, StandardCharsets.UTF_8).padToLong()
    return encode(murmur32)
}

fun encode(oct: Long): String {
    var octLong = BigInteger.valueOf(oct)
    val builder = StringBuilder(ShortUrlService.MAX_LEN)
    while (octLong != BigInteger.ZERO) {
        val reminder = octLong.divideAndRemainder(BigInteger.valueOf(ShortUrlService.BASE.toLong()))
        builder.append(ShortUrlService.ALPHABETS[(reminder[1].toInt())])
        octLong = reminder[0]
    }
    return builder.reverse().toString()
}