package com.scdt.service;

import com.scdt.util.Tools;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class ShortPathServiceImpl implements ShortPathService {
    /**
     * 内部存储1
     * shortPath -> longPath
     */
    private final ConcurrentHashMap<String, String> mapFromShortToLong = new ConcurrentHashMap<>();

    /**
     * 内部存储2
     * longPath -> shortPath
     */
    private final ConcurrentHashMap<String, String> mapFromLongToShort = new ConcurrentHashMap<>();

    private final static int SHORT_PATH_CHAR_LENGTH = 8;

    @GetMapping(value="/get_short_path/{long_path}")
    @Override
    public String getShortPath(@PathVariable("long_path") String longPath) {
        System.out.println(longPath);
        if (mapFromLongToShort.containsKey(longPath)) {
            return mapFromLongToShort.get(longPath);
        }
        String shortPath;

        //synchronized (this) { // we actually don't need to synchronize here
                                // because it's almost impossible
                                // to have key conflict in a tiny span of time.
        do {
            shortPath = Tools.getRandomString(SHORT_PATH_CHAR_LENGTH);
        } while (mapFromShortToLong.containsKey(shortPath));
        mapFromShortToLong.put(shortPath, longPath);
        mapFromLongToShort.put(longPath, shortPath);
        //}

        return shortPath;
    }

    @Override
    @GetMapping(value="/get_long_path/{short_path}")
    public String getLongPath(@PathVariable("short_path")String shortPath) {
        if (mapFromShortToLong.containsKey(shortPath)) {
            return mapFromShortToLong.get(shortPath);
        }
        return "";
    }
}
