package com.oldnoop.shortlink.transfer;

public interface LinkTransfer {
    String transfer(String link);
    String origin(String shortLink);
}
