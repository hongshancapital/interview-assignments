package com.scdt.java.shortLink.component.service;

public interface ShortLinkService {
    String getShortLink(String longLink);
    String restoreLongLink(String shortLink);
}
