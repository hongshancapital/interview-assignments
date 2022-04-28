package com.example.urlshortener.controller

import com.example.urlshortener.service.ShortUrlService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author  lilu - Li Lu (7939743@qq.com)
 * @version 1.0
 * @since   2022/04/19
 */
@RestController
@RequestMapping(value = ["api/v1"])
class ShortUrlController(
    @param:Autowired private val service: ShortUrlService
) {
    /**
     * Create a shorted URL
     * @param url
     * @param request
     * @return shorted URL
     */
    @RequestMapping(value = ["/shorten"], method = [RequestMethod.POST])
    fun shorten(@RequestBody(required = true) url: String, request: HttpServletRequest): ResponseEntity<String> {
        val shortUrl = request.requestURL.toString() + '/' + service.save(url)
        return ResponseEntity.ok(shortUrl)
    }

    /**
     * Get the original URL
     * @param id
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = ["/query/{id}"], method = [RequestMethod.GET])
    @Throws(IOException::class)
    fun query(@PathVariable("id") id: String, response: HttpServletResponse): ResponseEntity<String> {
        val originalUrl = service.getOriginalUrl(id)
        return ResponseEntity.ok(originalUrl)
    }
}