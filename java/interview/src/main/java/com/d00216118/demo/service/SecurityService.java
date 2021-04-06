package com.d00216118.demo.service;

/**
 * @author Yu Chen
 * @email D00216118@student.dkit.ie
 * @date 2:52 下午 2021/3/30
 **/
public interface SecurityService {

    String createToken(String subject, long ttlMillis);

    String getSubject(String token);

    boolean checkUrl(String url);

}
