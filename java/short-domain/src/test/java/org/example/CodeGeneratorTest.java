package org.example;

import org.example.exception.CodeGeneratorException;
import org.example.model.CodeGenerator;
import org.example.model.CodeGeneratorWithHashIds;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Stream;

@SpringBootTest
public class CodeGeneratorTest {

    @Autowired
    @Qualifier("hashIds")
    private CodeGenerator codeGeneratorWithHashIds;

    @Autowired
    @Qualifier("autoId")
    private CodeGenerator codeGeneratorWithAutoId;

    @BeforeEach
    public void beforeEach() throws Exception {
        Field currentStaticField = codeGeneratorWithHashIds.getClass().getDeclaredField("current");
        currentStaticField.setAccessible(true);
        currentStaticField.set(null, 1L);

        Field startNumberField = codeGeneratorWithAutoId.getClass().getDeclaredField("startNumber");
        startNumberField.setAccessible(true);
        startNumberField.set(null, 1L);
        Field endNumberField = codeGeneratorWithAutoId.getClass().getDeclaredField("endNumber");
        endNumberField.setAccessible(true);
        endNumberField.set(null, 218340105584895L);
        Field usedNumberField = codeGeneratorWithAutoId.getClass().getDeclaredField("usedNumber");
        usedNumberField.setAccessible(true);
        usedNumberField.set(null, new ConcurrentSkipListSet<>());
    }

    @Test
    @DisplayName("HashIds - 连续生成多个不重复的code")
    public void moreNewCode_withHashIds_test() throws CodeGeneratorException {
        int i = 0;
        int maxSize = 100000;
        Set<String> set = new HashSet<>();
        while (i < maxSize) {
            set.add(codeGeneratorWithHashIds.createNewCode());
            i++;
        }

        Assertions.assertEquals(maxSize, set.size());
    }

    @Test
    @DisplayName("HashIds - 并行下生成多个不重复的code")
    public void moreNewCode_parallel_withHashIds_test() throws CodeGeneratorException {
        Set<String> set = new ConcurrentSkipListSet<>();
        int maxSize = 100000;
        Stream.iterate(0, i -> i + 1).limit(maxSize).parallel().forEach(i -> {
            try {
                String newCode = codeGeneratorWithHashIds.createNewCode();
                set.add(newCode);
            } catch (CodeGeneratorException e) {
                e.printStackTrace();
            }
        });

        Assertions.assertEquals(maxSize, set.size());
    }

    @Test
    @DisplayName("HashIds - 生成超出长度的code，抛异常")
    public void createNewCode_hashIds_exception_test() throws Exception {
        Field currentStaticField = codeGeneratorWithHashIds.getClass().getDeclaredField("current");
        currentStaticField.setAccessible(true);
        currentStaticField.set(null, 999999999999L);

        Assertions.assertThrows(CodeGeneratorException.class, () -> {
            codeGeneratorWithHashIds.createNewCode();
        });
    }

    @Test
    @DisplayName("AutoId - 连续生成多个不重复的code")
    public void moreNewCode_withAutoId_test() throws CodeGeneratorException {
        int i = 0;
        int maxSize = 100000;
        Set<String> set = new HashSet<>();
        while (i < maxSize) {
            set.add(codeGeneratorWithAutoId.createNewCode());
            i++;
        }

        Assertions.assertEquals(maxSize, set.size());
    }

    @Test
    @DisplayName("AutoId - 并行下生成多个不重复的code")
    public void moreNewCode_parallel_withAutoId_test() {
        Set<String> set = new ConcurrentSkipListSet<>();
        int maxSize = 100000;
        Stream.iterate(0, i -> i + 1).limit(maxSize).parallel().forEach(i -> {
            try {
                String newCode = codeGeneratorWithAutoId.createNewCode();
                set.add(newCode);
            } catch (CodeGeneratorException e) {
                e.printStackTrace();
            }
        });

        Assertions.assertEquals(maxSize, set.size());
    }

    @Test
    @DisplayName("AutoId - id用尽，抛异常")
    public void createNewCode_autoid_random_exception_test() throws Exception {
        Field startNumberField = codeGeneratorWithAutoId.getClass().getDeclaredField("startNumber");
        startNumberField.setAccessible(true);
        startNumberField.set(null, 1L);
        Field endNumberField = codeGeneratorWithAutoId.getClass().getDeclaredField("endNumber");
        endNumberField.setAccessible(true);
        endNumberField.set(null, 2L);

        Set<String> set = new HashSet<>();
        set.add(codeGeneratorWithAutoId.createNewCode());
        set.add(codeGeneratorWithAutoId.createNewCode());
        Assertions.assertEquals(2, set.size());
        Assertions.assertThrows(CodeGeneratorException.class, () -> {
            codeGeneratorWithAutoId.createNewCode();
        });
    }
}
