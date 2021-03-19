package com.sxg.shortUrl.utils;

import java.net.URI;
import java.net.URISyntaxException;


/**
 * @author sxg
 */
public class UrlUtil {

    public static String arrChars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";


    public static String convertToStr(long num) throws RuntimeException {

        int n = arrChars.length();

        StringBuilder ret = new StringBuilder();
        do {
            int perNum = (int) (num % n);
            num = num / n;
            ret.insert(0, arrChars.charAt(perNum));
        } while (num > 1);

        return ret.toString();
    }
  public static void main(String[] args) {
System.out.println(convertToStr(292716982304771L));
}

    public static boolean isValidUrl(String urlString) {
        URI uri = null;
        try {
            uri = new URI(urlString);
        } catch (URISyntaxException e) {
            return false;
        }

        if (uri.getHost() == null) {
            return false;
        }
        if (uri.getScheme().equalsIgnoreCase("http") || uri.getScheme().equalsIgnoreCase("https")) {
            return true;
        }
        return false;
    }

}
