package com.example.tinyurl;

import com.example.tinyurl.dao.UrlDao;
import com.example.tinyurl.impl.SimpleTinyUrlHelper;
import com.example.tinyurl.service.TinyUrlGenerator;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Tiny Url REST Controller
 * @author hermitriver
 */
@RestController
public class TinyUrlController {
    public final static String INVALID_TINYURL_MESSAGE = "Invalid Tiny URL";
    @Autowired
    TinyUrlGenerator tinyUrlGenerator;

    @Autowired
    UrlDao urlDao;

    /**
     * Generate Tiny URL
     * @param targetUrl long target URL
     * @return tiny URL
     */
    @RequestMapping(value="/_generate", method = { RequestMethod.GET, RequestMethod.POST })
    public String generate(@RequestParam(value = "t", required=true) String targetUrl) {
        String tinyurl = tinyUrlGenerator.generate(targetUrl);
        boolean res = urlDao.save(tinyurl, targetUrl);
        return res ? tinyurl : "";
    }

    /**
     * Access Tiny URL and redirect to mapped long target URL
     * @param tinyUrl Tiny URL
     * @return error message if not succeed in redirecting
     */
    @GetMapping("/{tinyUrl}")
    public String redirect(HttpServletResponse response, @PathVariable("tinyUrl") String tinyUrl) throws IOException {
        // overlong tiny url
        if (tinyUrl.length()> SimpleTinyUrlHelper.BITS) {
            return INVALID_TINYURL_MESSAGE;
        }
        // invalid tiny url
        if (!Pattern.matches("^[A-Za-z0-9]+$", tinyUrl)) {
            return INVALID_TINYURL_MESSAGE;
        }

        String targetUrl = urlDao.getTargetUrl(tinyUrl);
        // no existing target url in database
        if (targetUrl == null) {
            return INVALID_TINYURL_MESSAGE;
        }

        // redirect to target url
        response.sendRedirect(targetUrl);
        return null;
    }
}
