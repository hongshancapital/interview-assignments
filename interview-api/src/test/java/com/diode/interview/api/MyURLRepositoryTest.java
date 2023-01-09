package com.diode.interview.api;

import com.diode.interview.infrastructure.repository.url.MyURLRepositoryImpl;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;

/**
 * @author unlikeha@163.com
 * @date 2022/4/29
 */
@Slf4j
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class MyURLRepositoryTest {

    @Mock
    private ApplicationContext applicationContext;

    @InjectMocks
    private MyURLRepositoryImpl myURLRepository;

    @Mock
    private Cache<String, Object> cache;

    @Test
    public void testSaveURL(){
        myURLRepository.saveURL(null, null, 0);

        doThrow(new RuntimeException("error!")).when(cache).put(anyString(), anyString());
        boolean b = myURLRepository.saveURL("aaa", "aaa", 1);
        Assert.assertFalse(b);
    }

    @Test
    public void testGetShortURL(){
        String shortURL = myURLRepository.getShortURL(null);
        Assert.assertTrue(Objects.isNull(shortURL));
    }

    @Test
    public void testGetLongURL(){
        String shortURL = myURLRepository.getLongURL(null);
        Assert.assertTrue(Objects.isNull(shortURL));
    }
}
