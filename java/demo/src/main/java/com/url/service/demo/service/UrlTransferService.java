package com.url.service.demo.service;

public interface UrlTransferService {

	String saveUrl(String fullUrl);

	String getFullUrl(String shortUrl);
}
