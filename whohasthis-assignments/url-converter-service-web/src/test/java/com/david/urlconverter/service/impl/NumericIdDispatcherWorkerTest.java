package com.david.urlconverter.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class NumericIdDispatcherWorkerTest {

    private NumericIdDispatcherWorker numericIdDispatherWorker;

    private LinkedList<Long> list;
    @BeforeEach
    public void beforeTest(){
        numericIdDispatherWorker = new NumericIdDispatcherWorker();
        list = new LinkedList<>();
        Long baseLong = 1000L;
        for(int i=0;i<100;i++){
            list.add(baseLong+i*10);
        }
        numericIdDispatherWorker.setIdList(list);
    }


    @Test
    public void testIsEmpty() {
        assertThat(numericIdDispatherWorker.isEmpty()).isEqualTo(false);
        numericIdDispatherWorker.setIdList(null);
        assertThat(numericIdDispatherWorker.isEmpty()).isEqualTo(true);
    }

    @Test
    public void testDispatchId() {
        assertThat(numericIdDispatherWorker.dispatchId()).isEqualTo(1000);
        assertThat(numericIdDispatherWorker.dispatchId()).isEqualTo(1010);
        assertThat(numericIdDispatherWorker.dispatchId()).isEqualTo(1020);
        for(int i=0; i<200; i++){
            new Thread(() -> {
                System.out.println(numericIdDispatherWorker.dispatchId()+",");
            }).start();
        }
    }


    @Test
    public void testGetIdList() {
        assertThat(numericIdDispatherWorker.getIdList().size()).isEqualTo(100);
    }

    @Test
    public void testEquals() {
        assertThat(numericIdDispatherWorker.equals(new NumericIdDispatcherWorker())).isEqualTo(false);
        assertThat(numericIdDispatherWorker.equals(numericIdDispatherWorker)).isEqualTo(true);
    }

    @Test
    public void testHashCode() {
        System.out.println(numericIdDispatherWorker.hashCode());
    }

    @Test
    public void testToString() {
        System.out.println(numericIdDispatherWorker.toString());
    }
}