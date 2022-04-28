package com.example.urlshortener

import com.example.urlshortener.model.ShortUrl

import com.example.urlshortener.repository.UrlRepository
import com.example.urlshortener.service.ShortUrlService
import org.junit.jupiter.api.Assertions.assertEquals

import org.mockito.Mockito
import org.junit.jupiter.api.Test;
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class UrlShortenerServiceTest {
    private val mockRepository = Mockito.mock(UrlRepository::class.java)
    private val service = ShortUrlService(mockRepository)

    @Test
    fun testSave() {
        val result = service.save("http://fakercorp.com/fake-long-path-with-many/")
        assertEquals(result, "C6IHIY")
    }

    @Test
    fun testGetOriginalUrl() {
        val ID = "C6IHIY"
        val URL = "http://fakercorp.com/fake-long-path-with-many/"
        val s = ShortUrl().apply {
            id = ID
            originalUrl = URL
        }
        `when`(mockRepository.findById(ID)).thenReturn(Optional.of(s))
        assertEquals(URL, service.getOriginalUrl(ID))
    }
}