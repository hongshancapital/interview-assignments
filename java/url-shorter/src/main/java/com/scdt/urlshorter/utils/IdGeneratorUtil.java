package com.scdt.urlshorter.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author ming.yang
 */
public class IdGeneratorUtil
{
    private IdGeneratorUtil()
    {
    }

    /**
     * 将原始字串内容生成ID
     *
     * @param originContent 原始字串内容
     * @return ID
     */
    public static String generate(String originContent)
    {
        if(originContent == null )
        {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder() ;
        MessageDigest digest;
        try
        {
            digest = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            return null ;
        }

        //生成一组length=16的byte数组
        byte[] digestBytes = digest.digest(originContent.getBytes(StandardCharsets.UTF_8)) ;

        for (byte digestByte : digestBytes)
        {
            //byte转int为了不丢失符号位， 所以&0xFF
            int digestValue = digestByte & 0xFF;
            //如果c小于16，就说明，可以只用1位16进制来表示，那么在前面补一个0
            if (digestValue < 16)
            {
                stringBuilder.append("0");
            }
            stringBuilder.append(Integer.toHexString(digestValue));
        }
        return stringBuilder.toString() ;
    }
}
