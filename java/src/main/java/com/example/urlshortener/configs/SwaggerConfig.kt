package com.example.urlshortener.configs

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.oas.annotations.EnableOpenApi
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

/**
 * @author  lilu - Li Lu (7939743@qq.com)
 * @version 1.0
 * @since   2022/04/19
 */
@Configuration
@EnableOpenApi
class SwaggerConfig {

    @Value("\${swagger.enabled}")
    var swaggerEnabled = false

    @Bean
    fun docket(): Docket {
        return Docket(DocumentationType.OAS_30)
            .apiInfo(apiInfo())
            .enable(swaggerEnabled)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.example.urlshortener.controller"))
            .paths(PathSelectors.any())
            .build()
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfo(
            "ShortUrl API",
            "This service providers Url Shortening service",
            "v1.0",
            null,
            Contact("Nobody", "https://nowhere.com", "00000@qq.com"),
             null, "null", emptyList()
        )
    }
}