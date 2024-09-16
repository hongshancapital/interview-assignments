package com.ttts.urlshortener.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ttts.urlshortener.business.SenderNumsBusiness;
import com.ttts.urlshortener.domain.SenderNums;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

@Slf4j
class PartitionSegmentLongValueCreateServiceImplTest {

    @Test
    void create() {
        String input = "123";

        Long startNum = 1001L;
        Long endNum = 2000L;
        SenderNums senderNums = new SenderNums();
        senderNums.setId(123L);
        senderNums.setStartNum(startNum);
        senderNums.setEndNum(endNum);

        SenderNumsBusiness senderNumsBusiness  = mock(SenderNumsBusiness.class);
        doReturn(senderNums).when(senderNumsBusiness).add();

        PartitionSegmentLongValueCreateServiceImpl target =
            spy(new PartitionSegmentLongValueCreateServiceImpl(senderNumsBusiness));


        int times = (int) (endNum - startNum + 1);
        for (int index = 0; index < times; index++) {
            Long actual = target.create(input);
            log.debug("index:{} actual:{}", index, actual);
            assertEquals(startNum + index, actual);
            verify(target, times(index + 1)).checkApplied();
        }
    }

    @Test
    void create_multiple_thread() throws InterruptedException {
        String input = "123";

        Long startNum = 1001L;
        Long endNum = 2000L;
        SenderNums senderNums = new SenderNums();
        senderNums.setId(123L);
        senderNums.setStartNum(startNum);
        senderNums.setEndNum(endNum);

        SenderNumsBusiness senderNumsBusiness  = mock(SenderNumsBusiness.class);
        doReturn(senderNums).when(senderNumsBusiness).add();

        PartitionSegmentLongValueCreateServiceImpl target =
            spy(new PartitionSegmentLongValueCreateServiceImpl(senderNumsBusiness));

        int threadNum = 10;
        int times = (int) (endNum - startNum + 1) / threadNum;

        CountDownLatch latch = new CountDownLatch(threadNum);

        Set<Long> result = new CopyOnWriteArraySet<>();

        Runnable runnable = () -> {
            for (int index = 0; index < times; index++) {
                Long actual = target.create(input);
                log.debug("index:{} actual:{}", index, actual);
                result.add(actual);
            }
            latch.countDown();
        };

        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }

        latch.await();

        log.debug("总共生成数量: {}", result.size());

        assertEquals(endNum + 1, target.getAtomicLong().longValue());
        assertEquals(endNum - startNum + 1, result.size());
        verify(target, times((int) (endNum - startNum + 1))).checkApplied();
    }

    @Test
    void create_exhaust() {
        String input = "123";

        Long step = 100L;

        int applyTimes = 5;
        SenderNums[] senderNumss = new SenderNums[applyTimes];
        for (int i = 0; i < applyTimes; i++) {
            SenderNums senderNums = new SenderNums();
            senderNums.setId(123L + i);
            senderNums.setStartNum(step * i + 1);
            senderNums.setEndNum(step * i + step);

            senderNumss[i] = senderNums;
        }

        SenderNumsBusiness senderNumsBusiness  = mock(SenderNumsBusiness.class);

        when(senderNumsBusiness.add()).thenReturn(senderNumss[0], senderNumss[1], senderNumss[2],
            senderNumss[3], senderNumss[4]);

        PartitionSegmentLongValueCreateServiceImpl target =
            spy(new PartitionSegmentLongValueCreateServiceImpl(senderNumsBusiness));


        int times = (int) (step * applyTimes);

        for (int index = 0; index < times; index++) {
            Long actual = target.create(input);
            log.debug("index:{} actual:{}", index, actual);
        }

        assertEquals(501, target.getAtomicLong().longValue());
        verify(target, times(5)).applySenderNums();
    }

    @RepeatedTest(2)
    @Test
    void create_exhaust_multiple_thread() throws InterruptedException {
        String input = "123";

        Long step = 100L;

        int applyTimes = 5;
        SenderNums[] senderNumss = new SenderNums[applyTimes];
        for (int i = 0; i < applyTimes; i++) {
            SenderNums senderNums = new SenderNums();
            senderNums.setId(Long.valueOf(i));
            senderNums.setStartNum(step * i + 1);
            senderNums.setEndNum(step * i + step);

            senderNumss[i] = senderNums;
        }

        SenderNumsBusiness senderNumsBusiness  = mock(SenderNumsBusiness.class);

        when(senderNumsBusiness.add()).thenReturn(senderNumss[0], senderNumss[1], senderNumss[2],
            senderNumss[3], senderNumss[4]);

        PartitionSegmentLongValueCreateServiceImpl target =
            spy(new PartitionSegmentLongValueCreateServiceImpl(senderNumsBusiness));

        int threadNum = 10;

        int totalNums = (int) (step * (applyTimes));

        int times =  totalNums / threadNum;

        CountDownLatch latch = new CountDownLatch(threadNum);

        Set<Long> result = new CopyOnWriteArraySet<>();

        AtomicInteger callNum = new AtomicInteger(0);

        Runnable runnable = () -> {
            for (int index = 0; index < times; index++) {
                Long actual = target.create(input);
                log.debug("callNum:{} index:{} actual:{}", callNum.incrementAndGet(), index, actual);
                boolean r = result.add(actual);
                if (!r) {
                    log.error("重复：{}", actual);
                }
                assertTrue(r);
            }
            latch.countDown();
        };

        for (int i = 0; i < threadNum; i++) {
            new Thread(runnable).start();
        }

        latch.await();

        log.debug("总共生成数量: {}", result.size());
        assertEquals(totalNums, result.size());
    }

    @Test
    public void tryApplySenderNums() {
        Long currentId = 123L;

        SenderNums mockSenderNums = mock(SenderNums.class);
        when(mockSenderNums.getId()).thenReturn(currentId);

        PartitionSegmentLongValueCreateServiceImpl target =
            spy(new PartitionSegmentLongValueCreateServiceImpl(null));

        doNothing().when(target).applySenderNums();
        doReturn(mockSenderNums).when(target).getCurrent();

        target.tryApplySenderNums(currentId);

        verify(target, times(1)).applySenderNums();
    }

    @Test
    public void tryApplySenderNums2() {
        Long currentId = 123L;
        Long id = 125L;

        SenderNums mockSenderNums = mock(SenderNums.class);
        when(mockSenderNums.getId()).thenReturn(id);

        PartitionSegmentLongValueCreateServiceImpl target =
            spy(new PartitionSegmentLongValueCreateServiceImpl(null));

        doNothing().when(target).applySenderNums();
        doReturn(mockSenderNums).when(target).getCurrent();

        target.tryApplySenderNums(currentId);

        verify(target, times(0)).applySenderNums();
    }
}