package com.ttts.urlshortener.repository.impl;

import com.ttts.urlshortener.base.Constant;
import com.ttts.urlshortener.domain.SenderNums;
import com.ttts.urlshortener.repository.SenderNumsRepository;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

/**
 * jvm存储发号片段
 */
@Repository
public class SenderNumsRepositoryImpl implements SenderNumsRepository {

    //内存模拟持久化数据
    public static List<SenderNums> SENDER_NUMS = new CopyOnWriteArrayList<>();

    //模拟主键
    private static volatile AtomicLong INDEX = new AtomicLong(1000000);

    @Override
    public SenderNums add() {
        long index = INDEX.incrementAndGet();

        long endNum = index * Constant.SENDER_NUMS_STEP;
        long startNum = endNum -  Constant.SENDER_NUMS_STEP + 1;

        SenderNums senderNums = new SenderNums();
        senderNums.setId(index);
        senderNums.setStartNum(startNum);
        senderNums.setEndNum(endNum);

        SENDER_NUMS.add(senderNums);
        return senderNums;
    }
}
