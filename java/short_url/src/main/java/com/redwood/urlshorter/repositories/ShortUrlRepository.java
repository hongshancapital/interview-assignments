package com.redwood.urlshorter.repositories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

@Repository
@Slf4j
public class ShortUrlRepository {

	private final Map<String, ShortUrl> keyToShortUrl = new HashMap<>();
	private final Map<String, ShortUrl> urlToShortUrl = new HashMap<>();

	public ShortUrl findByKey(String key) {
		return keyToShortUrl.get(key);
	}

	public ShortUrl findByUrl(String url) {
		return urlToShortUrl.get(url);
	}

	public ShortUrl create(ShortUrl url) {
		keyToShortUrl.put(url.getKey(), url);
		urlToShortUrl.put(url.getUrl(), url);
		return url;
	}
}
