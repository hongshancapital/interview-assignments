package com.zhouhongbing.shorturl.enm;

import com.zhouhongbing.shorturl.utils.IdWorker;
import org.springframework.beans.factory.annotation.Value;

/**
 * @version 1.0
 * @Author 海纳百川zhb
 **/
public enum IdworkerInstance {



    //Idworker的单例模式
     INSTANCE;

    @Value("${idworker.datacent}")
    private long idworkerDatacent;


    @Value("${idworker.nodeId}")
    private long idworkerNodeId;

    public IdWorker IdworkerInstance(){ return new IdWorker(idworkerDatacent,idworkerNodeId);};
}
