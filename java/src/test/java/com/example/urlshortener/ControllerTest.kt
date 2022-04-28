package com.example.urlshortener

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import  org.hamcrest.Matchers.containsString;
import  org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import  org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import  org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import  org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import  org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ShortUrlControllerTest {

    companion object {
        private const val BASE_PATH = "/api/v1"
    }

    @Autowired
    private val mockMvc: MockMvc? = null

    @Test
    @Throws(Exception::class)
    fun testShortenUrl() {
        val input = "http://fakercorp.com/fake-long-path-with-many/"
        mockMvc!!.perform(
            post("$BASE_PATH/shorten")
                .contentType(MediaType.APPLICATION_JSON).content(input)
        ).andDo(print())
            .andExpect(status().isOk)
            .andExpect(content().string(containsString("http://localhost$BASE_PATH/shorten/C6IHIY")))
            .andReturn()
    }

//    @Test
//    @Throws(java.lang.Exception::class)
//    fun testFindUrl() {
//        val id = "C6IHIY"
//        val longUrl = "http://fakercorp.com/fake-long-path-with-many/"
//        mockMvc!!
//            .perform(get("$BASE_PATH/query/$id"))
//            .andDo(print())
//            .andExpect(status().isOk)
//            .andExpect(content().string(containsString("C6IHIY")))
//    }
}