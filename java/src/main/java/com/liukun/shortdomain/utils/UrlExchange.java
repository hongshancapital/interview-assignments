package com.liukun.shortdomain.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.TreeBidiMap;
import org.springframework.stereotype.Component;

import java.text.Bidi;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * <b>Class name</b>:
 * </p>
 * <p>
 * <b>Class description</b>: Class description goes here.
 * </p>
 * <p>
 * <b>Author</b>: kunliu
 * </p>
 * <b>Change History</b>:<br/>
 * <p>
 *
 * <pre>
 * Date          Author       Revision     Comments
 * ----------    ----------   --------     ------------------
 * 2021/10/6       kunliu        1.0          Initial Creation
 *
 * </pre>
 *
 * @author kunliu
 * @date 2021/10/6
 * </p>
 */
@Component
@Slf4j
public class UrlExchange {

    BidiMap<String, String> urlMap = new TreeBidiMap<>();

    public String long2Short(String longUrl) {
        UrlEncoder urlEncoder = new UrlEncoder();
        if (urlMap.containsKey(longUrl)) {
            return urlMap.get(longUrl);
        }
        if (urlMap.size() >= 1L << 30) {
            new RuntimeException("map 已满");
        }
        urlMap.put(longUrl, urlEncoder.shorten());
        return urlMap.get(longUrl);
    }

    public String short2Long(String shortUrl) {
        return urlMap.getKey(shortUrl);
    }

}
