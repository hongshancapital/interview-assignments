package com.zhouhongbing.shorturl.enm;

import com.zhouhongbing.shorturl.utils.LRUCache;

/**
 * @version 1.0
 * @Author 海纳百川zhb
 **/
public  enum LRUCacheInstance {

     LRU_CACHE_INSTANCE;

//     public  LRUCache LRUCacheInstance(){
//         return new LRUCache(2*1024*1024);
//     }
     public  LRUCache LRUCacheInstance(){
         return new LRUCache(2*1024);
     }
}
