package interview.assignments.zhanggang.core.shortener.model;

import interview.assignments.zhanggang.config.exception.error.URLFormatException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;

@EqualsAndHashCode
@RequiredArgsConstructor
@Getter
public class Shortener {
    private static final String URL_SEPARATOR = "/";

    private final String id;
    private final String originalUrl;
    private final Instant createAt;

    public Shortener(String id, String url) {
        this.id = id;
        this.originalUrl = url;
        this.createAt = Instant.now();
    }

    public String getShortUrl(String host) {
        return host + URL_SEPARATOR + id;
    }

    public static String parseId(String shortUrl) {
        try {
            return new URL(shortUrl).getPath().replaceFirst(URL_SEPARATOR, "");
        } catch (MalformedURLException e) {
            throw new URLFormatException();
        }
    }
}
