package com.example.sequoiahomework;

import com.example.sequoiahomework.common.ex.MaximumMemoryException;
import com.example.sequoiahomework.config.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author Irvin
 * @description 异常测试
 * @date 2021/10/10 17:31
 */
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class ExTest {

    @Resource
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    public void testResult() {
        try {
            extracted(3);
        } catch (MaximumMemoryException e) {
            log.error("内存异常", e);
            globalExceptionHandler.maximumMemoryException(e);
        }
    }

    @Test
    public void testRunTime() {
        try {
            int i = 1/0;
        } catch (Exception e) {
            globalExceptionHandler.runtimeExceptionHandler(e);
        }
    }


    private void extracted(int i) throws MaximumMemoryException {
        if (i > 1) {
            throw new MaximumMemoryException("内存已满");
        }
    }

}
