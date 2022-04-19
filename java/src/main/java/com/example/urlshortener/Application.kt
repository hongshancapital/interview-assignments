package com.example.urlshortener

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * @author  lilu - Li Lu (7939743@qq.com)
 * @version 1.0
 * @since   2022/04/19
 */
@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}