package com.scdt.java.shortLink.component.dao;

public interface ShortLinkDAO {
    void saveLinkRelation(String key, String val);

    String getShortFromLong(String key);

    String getLongFromShort(String key);
}
