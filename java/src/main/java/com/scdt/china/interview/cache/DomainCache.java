package com.scdt.china.interview.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sujiale
 *
 */
public interface DomainCache {
    Map<String, String> SHORT_LONG = new HashMap<>();
    Map<String, String> LONG_SHORT = new HashMap<>();
}
