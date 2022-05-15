package com.oldnoop.shortlink.storage;

public interface LinkStorage {
    void save(String shortLink, String longLink);
    String getLongLink(String shortLink);
    String getShortLink(String longLink);
    void remove(String shortLink);
}
