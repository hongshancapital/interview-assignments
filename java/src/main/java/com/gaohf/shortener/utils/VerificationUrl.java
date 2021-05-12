package com.gaohf.shortener.utils;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * 验证Url
 * @author: Gaohf
 * @create: 2021-05-11 17:56:47
 **/
public class VerificationUrl {

    public static boolean isValidUrl(String url) {
        URL u = null;
        try {
            u = new URL(url);
        } catch (MalformedURLException e) {
            return false;
        }
        try {
            u.toURI();
        } catch (URISyntaxException e) {
            return false;
        }
        return true;
    }
}
