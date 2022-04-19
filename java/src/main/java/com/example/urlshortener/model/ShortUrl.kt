package com.example.urlshortener.model

import javax.persistence.*
import javax.validation.constraints.NotNull

/**
 * @author  lilu - Li Lu (7939743@qq.com)
 * @version 1.0
 * @since   2022/04/19
 */
@Entity
@Table(name = "short_urls")
class ShortUrl {
    @Id
    @Column(name = "id")
    var id: @NotNull String? = null

    @Column(name = "original_url")
    var originalUrl: @NotNull String? = null
}