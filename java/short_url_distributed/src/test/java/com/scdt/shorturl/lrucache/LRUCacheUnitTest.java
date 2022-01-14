package com.scdt.shorturl.lrucache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class LRUCacheUnitTest {

    @Test
    public void addSomeDataToCache_WhenGetData_ThenIsEqualWithCacheElement() {
        LRUCache<String, String> lruCache = new LRUCache<>(3);
        lruCache.put("1", "test1");
        lruCache.put("2", "test2");
        lruCache.put("3", "test3");
        assertEquals("test1", lruCache.get("1").get());
        assertEquals("test2", lruCache.get("2").get());
        assertEquals("test3", lruCache.get("3").get());
    }

    @Test
    public void addDataToCacheToTheNumberOfSize_WhenAddOneMoreData_ThenLeastRecentlyDataWillEvict() {
        LRUCache<String, String> lruCache = new LRUCache<>(3);
        lruCache.put("1", "test1");
        lruCache.put("2", "test2");
        lruCache.put("3", "test3");
        lruCache.put("4", "test4");
        lruCache.put("4", "test5");
        assertFalse(lruCache.get("1").isPresent());
        assertEquals("test5",lruCache.get("4").get());
    }

    @Test
    public void runMultiThreadTask_WhenPutDataInConcurrentToCache_ThenNoDataLost() throws Exception {
        final int size = 50;
        final ExecutorService executorService = Executors.newFixedThreadPool(5);
        Cache<Integer, String> cache = new LRUCache<>(size);
        CountDownLatch countDownLatch = new CountDownLatch(size);
        try {
            IntStream.range(0, size).<Runnable>mapToObj(key -> () -> {
                cache.put(key, "value" + key);
                countDownLatch.countDown();
            }).forEach(executorService::submit);
            countDownLatch.await();
        } finally {
            executorService.shutdown();
        }
        assertEquals(cache.size(), size);
        IntStream.range(0, size).forEach(i -> assertEquals("value" + i, cache.get(i).get()));
    }
    @Test
    public void testDummyNodeDetach(){
        DoublyLinkedList<String> doublyLinkedList =  new DoublyLinkedList<>();
        doublyLinkedList.removeTail();
        LinkedListNode<String> node = doublyLinkedList.updateAndMoveToFront(new DummyNode<>(doublyLinkedList),"test1");
        node.detach();
        node.getListReference();
        Assertions.assertEquals(node.getPrev(),node.getNext());
    }

    @Test
    public void testDummyNodeGetElement(){
        DoublyLinkedList<String> doublyLinkedList =  new DoublyLinkedList<>();
        doublyLinkedList.add("test1");
        doublyLinkedList.add("test2");
        doublyLinkedList.add("test3");
        doublyLinkedList.add("test4");
        doublyLinkedList.removeTail();
        LinkedListNode<String> node = doublyLinkedList.updateAndMoveToFront(new DummyNode<>(doublyLinkedList),"test1");
        doublyLinkedList.moveToFront(node);
        LRUCache<String,String> lruCache = new LRUCache<>(2);
        lruCache.put("1","test1");
        lruCache.put("2","test2");
        lruCache.put("1","test3");
        assertEquals("test3",lruCache.get("1").get());
        assertThrows(NullPointerException.class, node::getElement);
    }
}