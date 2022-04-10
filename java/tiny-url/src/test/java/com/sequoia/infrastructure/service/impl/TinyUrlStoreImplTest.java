package com.sequoia.infrastructure.service.impl;

import com.alibaba.testable.core.annotation.MockInvoke;
import com.alibaba.testable.core.model.MockScope;
import com.alibaba.testable.processor.annotation.EnablePrivateAccess;
import com.sequoia.infrastructure.util.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static com.alibaba.testable.core.tool.PrivateAccessor.invoke;

/**
 * TinyUrlStoreImplTest
 *
 * @author KVLT
 * @date 2022-04-05.
 */
@Slf4j
@EnablePrivateAccess(srcClass = TinyUrlStoreImpl.class)
public class TinyUrlStoreImplTest {

    private TinyUrlStoreImpl store = new TinyUrlStoreImpl();

    public static class Mock {

        @MockInvoke(targetClass = TinyUrlStoreImpl.class, scope = MockScope.ASSOCIATED)
        private String tinyCode(String originUrl) {
            return "test";
        }

        @MockInvoke(targetClass = TinyUrlStoreImpl.class, scope = MockScope.ASSOCIATED)
        private String getOriginUrl(String tinyCode) {
            return "originUrl";
        }

        @MockInvoke(targetClass = TinyUrlStoreImpl.class, scope = MockScope.ASSOCIATED)
        private CompletableFuture<String> generateTinyCodeFuture(String originUrl){
            CompletableFuture<String> future = new CompletableFuture<>();
            future.completeExceptionally(new InterruptedException());
            return future;
        }
    }

    @Test
    public void testGenerateTinyCodeFuture() throws Exception {
        try {
            CompletableFuture<String> future = new CompletableFuture<>();
            future.completeExceptionally(new InterruptedException());

            CompletableFuture<String> resultFuture = store.generateTinyCodeFuture("test");
            resultFuture.get();
        } catch (Exception e) {
            log.error("异常", e);
        }
    }

    @Test
    public void testGetTinyUrlFuture() {
        String originUrl = "www.sq.com";
        try {
            CompletableFuture<String> future = store.getTinyUrlFuture(originUrl);
            future.get();
        } catch (Exception e) {
            log.error("异常", e);
        }
    }

    @Test
    public void testGenerateTinyCode() {
        String originUrl = "www.sq.com";
        try {
            String tinyCode = store.generateTinyCode(originUrl);

            for (char c : Constant.DIGITS_CHAR_ARR) {
                String tinyCodeNew = c + StringUtils.substring(tinyCode, 1, tinyCode.length());
                store.putOriginUrl(tinyCodeNew, originUrl);
            }

            invoke(store, "putOriginUrl", tinyCode, originUrl);

//            tinyCode = store.generateTinyCode(originUrl);
        } catch (Exception e) {
            log.error("异常", e);
        }
    }

    @Test
    public void testSaveTinyOriginCodeMapping() {
        store.putOriginUrl("test", "originUrl");
        store.saveTinyOriginCodeMapping("test", "originUrl");
    }
}
