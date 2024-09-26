package com.jk.shorturl.Utils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Jiang Jikun
 * ShortCode
 * @Description 生成唯一序列号
 */
public class SequenceUtil {
    private static final long ID_MIN = (long)Math.pow(62 , 5);
    private static final long ID_MAX = (long) Math.pow(62, 8) - 1;
    private static final long ID_START =  ID_MIN;

    private static AtomicLong ID_CURRENT = new AtomicLong(ID_START);

     /**
     *  如果长度超过8位，则返最小值，循环使用。
     */
    public static long getNextId() {
        long id = ID_CURRENT.getAndIncrement();
        if(id> SequenceUtil.ID_MAX){
            ID_CURRENT.set(SequenceUtil.ID_MIN);
            id = ID_CURRENT.getAndIncrement();;
        }
        return id;
    }

}
