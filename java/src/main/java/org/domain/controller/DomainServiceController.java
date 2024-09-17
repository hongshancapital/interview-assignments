package org.domain.controller;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.domain.component.RateLimiterComponent;
import org.domain.domain.Link;
import org.domain.service.LinkService;
import org.domain.util.BinaryConversion;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class DomainServiceController {
    private Cache<String, String> cache = CacheBuilder.newBuilder().maximumSize(100).build();

    @Resource
    private RateLimiterComponent rateLimiterComponent;

    @Resource
    private LinkService linkService;

    @RequestMapping("/short")
    @ResponseBody
    public String longToShort(String longUrl) {
        if (!rateLimiterComponent.tryAcquire()) {
            return "too many request";
        }

        Link link = linkService.getByUrl(longUrl);
        if (link != null) {
            return BinaryConversion.tenToSixtyTwo(link.getId());
        }

        int id = linkService.insertLink(longUrl);
        return BinaryConversion.tenToSixtyTwo(id);
    }

    @RequestMapping("/long")
    @ResponseBody
    public String shortToLong(String shortUrl) {
        if (!rateLimiterComponent.tryAcquire()) {
            return "too many request";
        }

        try {
            return cache.get(shortUrl, () ->
                    linkService.getById(BinaryConversion.sixtyTwoToTen(shortUrl)).getUrl()
            );
        } catch (Exception e) {
            throw new RuntimeException("get llong url failed", e);
        }
    }
}
