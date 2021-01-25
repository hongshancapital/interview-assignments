package com.luman.shorturl.common.redis;


public class CacheKeyGenerator {
    private static final String CACHE_KEY_SPLITTER=":";
    private static String appName;
    public static String generateKey(String prefix,Object... tags){
        StringBuilder builder=new StringBuilder(prefix);
        if(tags!=null && tags.length>0){
            builder.append(CACHE_KEY_SPLITTER);
            for(int i=0,l=tags.length;i<l;i++){
                builder.append(tags[i]);
                if(i<l-1){
                    builder.append(CACHE_KEY_SPLITTER);
                }
            }
        }
        return builder.toString();
    }
}
