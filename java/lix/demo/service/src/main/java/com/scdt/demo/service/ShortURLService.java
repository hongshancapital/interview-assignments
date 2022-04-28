package com.scdt.demo.service;

import java.util.Optional;

public interface ShortURLService {

    String shortenUrl(String originUrl);

    Optional<String> getOriginUrl(String shortUrl);

}
