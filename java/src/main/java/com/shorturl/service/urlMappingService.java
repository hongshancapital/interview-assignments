package com.shorturl.service;

public interface urlMappingService {

    String urlCompress(String url) throws Exception;

    String urlDecompress(String base62) throws Exception;
}
