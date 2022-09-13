package io.nigelwy.javaassignments.repository;

public interface UrlRepository {

    void save(String shortUrl, String originUrl);

    String get(String shortUrl);
}
