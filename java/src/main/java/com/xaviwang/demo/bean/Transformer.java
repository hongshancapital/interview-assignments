package com.xaviwang.demo.bean;

import java.util.HashMap;
import java.util.Map;

public class Transformer {
	/** 
	 * Object mapFromOriginalUrlToTinyUrl is used to restore the mapping relationship from Original URL to Tiny URL.
	 * Key is original URL while value is tiny URL.
	 */
    protected Map<String, String> mapFromOriginalUrlToTinyUrl; 
    /** 
	 * Object mapFromTinyUrlToOriginalUrl is used to restore the mapping relationship from Tiny URL to Original URL.
	 * Key is tiny URL while value is original URL.
	 */
    protected Map<String, String> mapFromTinyUrlToOriginalUrl; 
    /**
     * Object maxId is to restore the current max ID in the system, it's in integer and used in 10 bits encoding tiny URL mode. It's volatile object to avoid concurrent issue in multiple thread mode.
     */
    protected volatile int maxId;
    /**
     * OBJECT START_ID is the initial ID of in the system, it's in integer format and used in 10 bits encoding tiny URL mode. It's final object to avoid unexpected code change.
     */
    private final int START_ID = 0;
    /**
     * Object maxLongId is to restore the current max ID in the system, it's in long format and used in 62 bits encoding tiny URL mode. It's volatile object to avoid concurrent issue in multiple thread mode.
     */
    protected volatile long maxLongId;
    /**
     * OBJECT START_LONG_ID is the initial ID of in the system, it's in long format and used in 62 bits encoding tiny URL mode. It's final object to avoid unexpected code change.
     */
    private final long START_LONG_ID = 0L;
    /**
     * Object MAX_BIT is to control the max bit of tiny URL in the system in one place. It's final object to avoid unexpected code change.
     */
    private final int MAX_BIT = 8;
    /**
     * Object MAKE_UP_BIT defines the default character when the calculated tiny URL is not up to MAX_BIT.
     */
    private final char MAKE_UP_BIT = '0';
    /**
     * Object DEFAULT_SHORT_URL defines the default tiny URL of the Transformer class.
     */
    private final String DEFAULT_TINY_URL = "########";
    /**
     * Object DEFAULT_TINY_URL_PREFIX defines the default tiny URL prefix of the Transformer class.
     */
    public final String DEFAULT_TINY_URL_PREFIX = "www.tinyurl.com/";
    
    /**
     * Construct function to initialize Transformer object.
     */
    public Transformer() {
        this.mapFromOriginalUrlToTinyUrl = new HashMap<>();
        this.mapFromTinyUrlToOriginalUrl = new HashMap<>();
        this.maxId = START_ID;
        this.maxLongId = START_LONG_ID;
    }
    
    /**
     * Getter function of object mapFromOriginalUrlToTinyUrl.
     * @return mapFromOriginalUrlToTinyUrl object that maintains mapping relationship from original URL to tiny URL.
     */
    public Map<String, String> getMapFromOriginalUrlToTinyUrl() {
        return this.mapFromOriginalUrlToTinyUrl;
    }
    
    /**
     * Getter function of object mapFromTinyUrlToOriginalUrl.
     * @return mapFromTinyUrlToOriginalUrl object that maintains mapping relationship from tiny URL to original URL.
     */
    public Map<String, String> getMapFromTinyUrlToOriginalUrl() {
        return this.mapFromTinyUrlToOriginalUrl;
    }

    /**
     * Getter function of object maxId.
     * @return maxId object that maintains the current max ID in the system of tiny URL.
     */
    public int getMaxId() {
        return this.maxId;
    }
    
    /**
     * Update function of maxId. It's synchronized to avoid concurrent conflict in multiple thread scenario.
     */
    public synchronized void updateMaxId() {
        this.maxId ++;
    }

    /**
     * Getter function of object maxLongId.
     * @return maxLongId object that maintains the current max ID in the system of tiny URL.
     */
    public long getMaxLongId() {
        return this.maxLongId;
    }

    /**
     * Update function of maxLongId. It's synchronized to avoid concurrent conflict in multiple thread scenario.
     */
    public synchronized void updateMaxLongId() {
        this.maxLongId ++;
    }

    /**
     * Function makeUpToEightBits is to make up missing bit ahead of the generated tiny URL when the bits is smaller than MAX_BIT.
     * @param originalUrl, the original URL customer provides.
     * @return tiny URL with length in MAX_BIT.
     */
    protected String makeUpToEightBits(String originalUrl) {
        StringBuffer result = new StringBuffer();
        int gap = MAX_BIT - originalUrl.length();
        while (gap != 0) {
            result.append(MAKE_UP_BIT);
            gap --;
        }
        result.append(originalUrl);
        return result.toString();
    }

    /**
     * Function transformFromOriginalUrlToTinyUrl provides the default action of transforming original URL to Tiny URL, this default method will be overridden in sub classes which inherited from class Transformer.
     * @param originalUrl, the original URL customer provides. 
     * @return DEFAULT_TINY_URL_PREFIX + DEFAULT_TINY_URL which just an example for the sub classes.
     */
    public String transformFromOriginalUrlToTinyUrl(String originalUrl) {
    	String result = DEFAULT_TINY_URL_PREFIX + DEFAULT_TINY_URL;
    	this.getMapFromOriginalUrlToTinyUrl().put(originalUrl, result);
    	this.getMapFromTinyUrlToOriginalUrl().put(result, originalUrl);
        return result;
    }
    
    /**
     * Function transformFromTinyUrlToOriginalUrl is to provide customer an API to get back original URL.
     * @param tinyUrl, the Tiny URL the system generates.
     * @return the original URL which customer provides.
     */
    public String transformFromTinyUrlToOriginalUrl(String tinyUrl) {
        String result = this.getMapFromTinyUrlToOriginalUrl().get(tinyUrl);
        return result;
    }
}
