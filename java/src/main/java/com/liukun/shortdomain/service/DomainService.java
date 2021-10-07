package com.liukun.shortdomain.service;

import com.liukun.shortdomain.utils.UrlEncoder;
import com.liukun.shortdomain.utils.UrlExchange;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.TreeBidiMap;
import org.springframework.stereotype.Service;

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
@Service
@AllArgsConstructor
public class DomainService {

    private UrlExchange urlExchange;

    public String createShortUrl(String longUrl) {
        return urlExchange.long2Short(longUrl.trim());
    }

    public String getLongUrl(String shortUrl) {
        return urlExchange.short2Long(shortUrl);
    }

}
