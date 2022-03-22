package cn.scdt.shorturl.service;


public interface ShortURLService {

    String getFullURL(String shortURL);

    String getShortURL(String longURL,String shortUrl);

}
