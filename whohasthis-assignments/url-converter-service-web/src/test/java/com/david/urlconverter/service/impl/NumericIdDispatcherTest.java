package com.david.urlconverter.service.impl;

import com.david.urlconverter.model.web.IdRange;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class NumericIdDispatcherTest {

    @Autowired
    private NumericIdDispatcher numericIdDispatcher;


    @Test
    public void testInitWorkerGroup() {
        //test avaiableCount not null
        numericIdDispatcher.initWorkerGroup();
        assertThat(numericIdDispatcher.getAvaiableCount().get()).isEqualTo(10000);
        assertThat(numericIdDispatcher.getWorkerGroup().size()).isEqualTo(10);
        assertThat(numericIdDispatcher.getWorkerGroup().get(0).getIdList().size()).isEqualTo(1000);
        System.out.println(numericIdDispatcher.getCurrentIdRange());
        System.out.println(numericIdDispatcher.getWorkerGroup());

        //test nextIdRange not null
        numericIdDispatcher.setCurrentIdRange(null);
        numericIdDispatcher.setNextIdRange(new IdRange(100L, 200L));
        numericIdDispatcher.setAvaiableCount(null);
        numericIdDispatcher.initWorkerGroup();
        assertThat(numericIdDispatcher.getAvaiableCount().get()).isEqualTo(100);
        assertThat(numericIdDispatcher.getWorkerGroup().size()).isEqualTo(10);
        assertThat(numericIdDispatcher.getWorkerGroup().get(0).getIdList().size()).isEqualTo(10);
        System.out.println(numericIdDispatcher.getCurrentIdRange());
        System.out.println(numericIdDispatcher.getWorkerGroup());

        //test nextIdRange null
        numericIdDispatcher.setNextIdRange(null);
        numericIdDispatcher.setCurrentIdRange(new IdRange(100L, 200L));
        numericIdDispatcher.setAvaiableCount(null);
        numericIdDispatcher.initWorkerGroup();
        assertThat(numericIdDispatcher.getAvaiableCount().get()).isEqualTo(100);
        assertThat(numericIdDispatcher.getWorkerGroup().size()).isEqualTo(10);
        assertThat(numericIdDispatcher.getWorkerGroup().get(0).getIdList().size()).isEqualTo(10);
        System.out.println(numericIdDispatcher.getCurrentIdRange());
        System.out.println(numericIdDispatcher.getWorkerGroup());


        //test nextIdRange null & currentIdRange null
        numericIdDispatcher.setNextIdRange(null);
        numericIdDispatcher.setCurrentIdRange(null);
        numericIdDispatcher.setAvaiableCount(null);
        numericIdDispatcher.initWorkerGroup();
        assertThat(numericIdDispatcher.getAvaiableCount().get()).isEqualTo(10000);
        assertThat(numericIdDispatcher.getWorkerGroup().size()).isEqualTo(10);
        assertThat(numericIdDispatcher.getWorkerGroup().get(0).getIdList().size()).isEqualTo(1000);
        System.out.println(numericIdDispatcher.getCurrentIdRange());
        System.out.println(numericIdDispatcher.getWorkerGroup());
    }

    @Test
    public void testGetNextId() {
        numericIdDispatcher.setNextIdRange(null);
        numericIdDispatcher.setCurrentIdRange(new IdRange(100L, 200L));
        numericIdDispatcher.setAvaiableCount(null);
        numericIdDispatcher.initWorkerGroup();
        log.info(numericIdDispatcher.toString());
        for(int i=0;i<200;i++){
            final int j = i;
            new Thread(() -> {
                try {
                    log.info(" Thread " + j + " get id:" + numericIdDispatcher.getNextId());
                } catch (Exception e) {
                    log.error("Thread "+ j + " exception:" , e);
                }
            }).start();
        }
    }

    @Test
    public void testPopulateNextRange() {
        numericIdDispatcher.setNextIdRange(null);
        numericIdDispatcher.populateNextRange();
        assertThat(numericIdDispatcher.getNextIdRange().getEndNum()
                -numericIdDispatcher.getNextIdRange().getStartNum()).isEqualTo(10000L);
    }

    @Test
    public void testEquals() {
        assertThat(numericIdDispatcher.equals(new NumericIdDispatcher())).isEqualTo(false);
        assertThat(numericIdDispatcher.equals(numericIdDispatcher)).isEqualTo(true);
    }

    @Test
    public void testHashCode() {
        System.out.println(numericIdDispatcher.hashCode());
    }

    @Test
    public void testToString() {
        System.out.println(numericIdDispatcher.toString());
    }


}