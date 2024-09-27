package com.skyscreen.urldemo.cache;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MapCache {

    //key is for longUrl, value is for shortUrl, multiple threads
    public static Map<String, String> map = new ConcurrentHashMap<String, String>();

    private static Logger logger = LoggerFactory.getLogger(MapCache.class);


    @PostConstruct
    public static void init(){
        logger.info("====init map====:" + map.size());
    }

    /**
     * 通过长链接值找出短链接key
     * @param value
     * @return
     */
    public static String getLongKey(String value) {
        String ret = StringUtils.EMPTY;
        if (map.containsValue(value)) {
            // iterate each entry of hashmap
            for(Map.Entry<String, String> entry: map.entrySet()) {

                // if give value is equal to value from entry
                // print the corresponding key
                if(entry.getValue().equalsIgnoreCase(value)) {
                    logger.info("The key for value " + value + " is " + entry.getKey());
                    ret = entry.getKey();
                    break;
                }
            }
        }
        return ret;
    }

}
