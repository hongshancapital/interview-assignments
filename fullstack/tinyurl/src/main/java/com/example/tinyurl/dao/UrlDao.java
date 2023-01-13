package com.example.tinyurl.dao;

/**
 * URL Data Access Object
 * @author hermitriver
 */
public interface UrlDao {
    /**
     * Get the max id in url table
     * @return the existing max id
     */
    public Long getMaxId();

    /**
     * Get long target url by tiny url
     * @param tinyUrl tiny url
     * @return long target url
     */
    public String getTargetUrl(String tinyUrl);

    /**
     * Save tiny url with its long target url into database
     * @param tinyUrl tiny url
     * @param targetUrl long target URL
     * @return true if save successfully
     */
    public boolean save(String tinyUrl, String targetUrl);
}
