package com.sequoia.shorturl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 *
 *@ DESC
 *
 *@Author xiaojun
 *
 *@Date 2021/6/27 16:07
 *
 *@version v1.0
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrl {
    private long id; //编号
    private String url; //原url
    private String shortUrl; //短url
}
