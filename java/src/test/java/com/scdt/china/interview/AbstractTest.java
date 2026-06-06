package com.scdt.china.interview;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author sujiale
 *
 */
@SuppressWarnings("rawtypes")
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = InterviewJavaApplication.class)
@ActiveProfiles("test,dev")
public abstract class AbstractTest extends AbstractTestNGSpringContextTests {
    public static final ObjectMapper objectMapper = new ObjectMapper();

    @DataProvider(name = "json")
    public static Object[][] loadJson(Method m) throws IOException {
        DataFile d = m.getAnnotation(DataFile.class);
        final InputStream inputStream = ClassLoader.getSystemResourceAsStream(d.path());
        List<?> data = objectMapper.readValue(inputStream, List.class);
        Object[][] objects = new Object[data.size()][m.getParameterCount()];
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) instanceof List) {
                List<?> list = (List) data.get(i);
                for (int j = 0; j < list.size(); j++) {
                    objects[i][j] = list.get(j);
                }
            } else {
                throw new IllegalArgumentException("测试数据需要是Object[][]数组");
            }
        }
        return objects;
    }
}
