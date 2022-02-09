package pers.dongxiang.shorturl.util;

import pers.dongxiang.shorturl.exception.OverLimitException;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @ProjectName short-url
 * @Package pers.dongxiang.shorturl.util
 * @ClassName IdUtil
 * @Description 发号器 62进制生成7位短地址，十进制需要从56800235584开始，到3521614606207结束，共3464814370623
 * @Company lab
 * @Author dongxiang
 * @Date 10/31/2021 6:37 PM
 * @UpdateUser
 * @UpdateDate
 * @UpdateRemark
 * @Version 1.0.0
 */
public class IdUtil {

    private static ConcurrentLinkedQueue<Long> queue = new ConcurrentLinkedQueue();

    private static Long start = 56800235583L;

    private static Long end = 3521614606207L;
    // private static Long end = 56800235585L;

    // 每次号码增长步数，分布式下可调整
    private static int step = 1;

    private static Object sync = new Object();

    public static Long nextId() throws OverLimitException{
        Long id;
        do{
            // 判断队列为空
            if (queue.isEmpty()){
                // 锁定只有一个线程通过
                synchronized (sync){
                    // 防止第二个拿到锁的线程进入
                    if (queue.isEmpty()){
                        for (int i = 1;i <= 10000;i++){
                            start += step;
                            if (start > end && i == 1){
                                throw new OverLimitException();
                            }else if (start > end){
                                continue;
                            }
                            queue.offer(start);
                        }
                    }
                }
            }
            id = queue.poll();
        }while(null == id);

        return id;
    }

}
