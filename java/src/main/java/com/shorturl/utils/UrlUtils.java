package com.shorturl.utils;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.escape.Escaper;
import com.google.common.net.UrlEscapers;
import com.linkedin.urls.NormalizedUrl;
import com.linkedin.urls.Url;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.regex.Pattern;

public class UrlUtils {
	
	public static final int MAX_URL_LENGHT = 255;

	public static final int MIN_URL_LENGHT = 20;

	/**
	 * 匹配简单的url
	 */
	private static Pattern urlPattern = Pattern.compile("^(https?|ftp)://([a-zA-Z0-9-\\.]+)(/?.*)");
	
	private static Splitter urlSplitter = Splitter.on("/").trimResults().omitEmptyStrings().limit(3);
	
	private static Splitter querySplitter = Splitter.on("&");
	
	private static Splitter pathSplitter = Splitter.on("/");
	
	private static CharMatcher hostCharMatcher = CharMatcher.JAVA_LETTER_OR_DIGIT.or(CharMatcher.anyOf(".-"));


    public static Boolean validateUrl(String url) {
        try {
            new URI(url);
        } catch (URISyntaxException e) {
            return false;
        }

        return true;
    }

    public static String normalizeUrl(String url) throws MalformedURLException {
		NormalizedUrl normalizedUrl = Url.create(url).normalize();

		return normalizedUrl.getFullUrl();
	}
	
	private static boolean validateHost(String host) {
		boolean status = hostCharMatcher.matchesAllOf(host);
		if (status) {
			if (host.startsWith("-") || host.endsWith("-") || host.contains("--")) {
				return false;
			}
			if (host.startsWith(".") || host.contains("..")) {
				return false;
			}
			if (host.contains(".-") || host.contains("-.")) {
				return false;
			}
			
			if (host.endsWith(".")) {
				host = host.substring(0, host.length() - 1);
			}
			return host.contains(".") ? true : false;
		}
		 
		return false;
	}
	
	public static String encodeUrl(String url) throws MalformedURLException {
		URL u = new URL(url);
		String path = u.getPath();
		String query = u.getQuery();
		String fragment = u.getRef();
		
		StringBuilder sb = new StringBuilder();
		sb.append(u.getProtocol());
		sb.append("://");
		sb.append(u.getHost());
		
		if (!path.isEmpty()) {
			path = encodePath(path);
			sb.append(path);
		}
		
		if (query != null && !query.isEmpty()) {
			query = encodeQuery(query);
			sb.append("?");
			sb.append(query);
		}
		
		if (fragment != null && !fragment.isEmpty()) {
			fragment = encodeFragment(fragment);
			sb.append("#");
			sb.append(fragment);
		}
		
		return sb.toString();
	}
	
	public static String encodePath(String path) {
		if (path.isEmpty() || path.equals("/")) {
			return path;
		}
		
		StringBuilder sb = new StringBuilder();
		Escaper escaper = UrlEscapers.urlPathSegmentEscaper();
		Iterable<String> iterable = pathSplitter.split(path);
		Iterator<String> iterator = iterable.iterator();
		while (iterator.hasNext()) {
			String part = iterator.next();
			if (part.isEmpty()) {
				sb.append("/");
				continue;
			}
			
			part = escaper.escape(part);
			sb.append(part);
			if (iterator.hasNext()) {
				sb.append("/");
			}
		}
		
		return sb.toString();
	}
	
	public static String encodeQuery(String query) {
		Escaper escaper = UrlEscapers.urlFormParameterEscaper();
		StringBuilder sb = new StringBuilder();
		
		Iterable<String> keyValueIterable = querySplitter.split(query);
		Iterator<String> iterator = keyValueIterable.iterator();
		while(iterator.hasNext()) {
			String keyValue = iterator.next();
			if (keyValue.isEmpty()) {
				if (iterator.hasNext()) {
					sb.append("&");
				}
				continue;
			}
			
			if (keyValue.equals("=")) {
				sb.append(keyValue);
				if (iterator.hasNext()) {
					sb.append("&");
				}
				continue;
			}
			
			int index = keyValue.indexOf('=');
			if (index == -1) {
				keyValue = escaper.escape(keyValue);
				sb.append(keyValue);
				if (iterator.hasNext()) {
					sb.append("&");
				}
				continue;
			}
			
			String key = keyValue.substring(0, index);
			if (index == 0) {
				key = "";
			}
			String value = "";
			if (index + 1 < keyValue.length()) {
				value = keyValue.substring(index + 1, keyValue.length());
			}
			
			if (!key.isEmpty()) {
				key = escaper.escape(key);
				sb.append(key);
				sb.append("=");
			}
			if (!value.isEmpty()) {
				value = escaper.escape(value);
				sb.append(value);
				if (iterator.hasNext()) {
					sb.append("&");
				}
			}
		}
		
		return sb.toString();
	}
	
	public static String encodeFragment(String fragment) {
		return UrlEscapers.urlFragmentEscaper().escape(fragment);
	}
}
