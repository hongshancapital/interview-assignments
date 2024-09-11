package com.example.shortlink.db;


import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * memory db storage utils for short link and long link.
 * the class is an utils class for short/link storage and
 * maintains two maps of short-long link and long-short link reversed.
 *
 *
 * there's one problem here :  that is the memory of MemoryDB occupied is limited,
 * so we have to find some strategy to avoid like FIFO , LRU or time-stamped entry map.
 * when memory storage exhausted , we can remove some entry by strategy.
 */
@Component
public class MemoryDB {


    /**
     * short - long link map
     */
    private ConcurrentHashMap<String, String> shortLongLinkMap = new ConcurrentHashMap<>();


    /**
     * long - short link map . the actually key that we keep is MD5(long link)
     */
    private ConcurrentHashMap<String, String> longShortLinMap = new ConcurrentHashMap<>();


    /**
     * store the short ,long link into shortLongLinkMap
     * @param shortLink
     * @param longLink
     * @return true if succeed , or false.
     */
    public boolean storeShortLink(String shortLink , String longLink){
        boolean result = false ;
        try {
            shortLongLinkMap.put(shortLink, longLink);
            result = true;
        } catch (Exception e) {
            // log the exception
        }

        return result ;
    }

    /**
     * store the short ,long link into shortLongLinkMap
     * @param shortLink
     * @param md5LongLink the md5 encoded long link
     * @return true if succeed , or false.
     */
    public boolean storeLongLink(String shortLink , String md5LongLink){
        boolean result = false ;
        try {
            longShortLinMap.put(md5LongLink, shortLink);
            result = true;
        } catch (Exception e) {
            // log the exception
        }

        return result ;
    }


    /**
     * get the short link mapped by md5 long link
     * @param md5LongLink md5 encoded long link
     * @return the short link
     */
    public String loadShortLink(String md5LongLink){
        String result = null ;
        try {
            result = longShortLinMap.get(md5LongLink);
        } catch (Exception e) {

        }
        return result;
    }




    /**
     * get the short link mapped by md5 long link
     * @param shortLink short link
     * @return the long link
     */
    public String loadLongLink(String shortLink){
        String result = null ;
        try {
            result = shortLongLinkMap.get(shortLink);
        } catch (Exception e) {

        }
        return result;
    }



}
