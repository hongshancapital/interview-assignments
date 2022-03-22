package com.zc.shorturl.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UrlUtils {
    public static String getDomain(String destination){
        if(destination == null || destination.trim().equals("")){
            return Strings.EMPTY;
        }

        String domain = "";
        URL url;
        try {
            url= new URL(destination);
            domain = url.getProtocol() +"://" + url.getHost();
        } catch (MalformedURLException e) {
            log.error("url=["+destination+"] not correct!");;
        }
        return domain;
    }
}
