package com.scdt.demo.dao;

import com.scdt.demo.dao.caffeine.CaffeineRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DaoConfig.class)
public class DaoTest {

    @Autowired
    private CaffeineRepository caffeineRepository;

    @Test
    public void caffeineTest() {
        caffeineRepository.put("1", "2");
        Optional<String> op1 = caffeineRepository.get("1", String.class);
        assertTrue(op1.isPresent());
        Optional<Integer> op2 = caffeineRepository.get("1", Integer.class);
        assertFalse(op2.isPresent());
    }
}
