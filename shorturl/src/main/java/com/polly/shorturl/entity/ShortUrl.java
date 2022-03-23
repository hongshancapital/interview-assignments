package com.polly.shorturl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author polly
 * @date 2022.03.20 11:01:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ShortUrl implements Serializable {
    private static final long serialVersionUID = -3368542116687838153L;
    private Long id;
    private String url;
}
