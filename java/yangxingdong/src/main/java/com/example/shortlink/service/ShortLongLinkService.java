package com.example.shortlink.service;


import com.example.shortlink.db.MemoryDB;
import com.example.shortlink.galting.Galting;
import com.example.shortlink.util.ShortLinkUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;


/**
 * short long link service providers the following functions :
 *
 *   F1. acquire short link by long link.
 *   F2. acquire long link by short link.
 *
 *
 * 1. acquire short link by long link .
 *
 *    . step 1.when a long - short convert request comes ,
 *
 *    . step 2.we first checkout {@code longShortLinkMap} in class MemoryDB , if exists just return the
 *  *             related value .
 *
 *    . step 3.short link don't exist in longShortLinkMap , then we acquire the "Galting" for a number,
 *             and then convert it by 62 radix , and then store the converted-62 radixed string and long
 *             link in the shortLongLinkMap
 *
 *    . step 4. if step 3 succeed , we also put the MD5(long link) - shortLink entry in the
 *              {@code longShortLinkMap} in MemoryDB ,so we fix the second function.
 *
 * 2. acquire short link by long link.
 *
 *    . step 1. MD5 the long link .
 *    . step 2. get the value  of longShortLinkMap by MD5(long link) key
 */
@Service
public class ShortLongLinkService {

    @Autowired
    private MemoryDB memoryDB;
    @Autowired
    private Galting galting;


    /**
     * F1. acquire short link by long link.
     *
     * @param longLink
     * @return the short link associated with long link
     */
    public String acquireShortLink(String longLink) {
        Assert.notNull(longLink,"long link can not be null");

        String result = null;
        try {
            String md5LongLink = DigestUtils.md5DigestAsHex(longLink.getBytes());
            result = memoryDB.loadShortLink(md5LongLink);

            if(result == null){
                // get an number from galting
                Long ticket = galting.shootNum();
                // 62 radix
                result = ShortLinkUtils.toBase62(ticket);
                // store the longShortLinMap
                memoryDB.storeLongLink(md5LongLink,result);
                memoryDB.storeShortLink(result,longLink);
            }

        } catch (Exception e) {}




        return result ;
    }


    /**
     * F2. acquire long link by short link.
     * @param shortLink
     * @return the long link associated with short link
     */
    public String acquireLongLink(String shortLink){
        Assert.notNull(shortLink,"short link can not be null");

        String result = null ;
        try{
            result = memoryDB.loadLongLink(shortLink);
        }catch (Exception e){}


        return result ;

    }

}
