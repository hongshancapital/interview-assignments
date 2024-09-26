package com.jinblog.shorturl.entry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LRUNodeTest {

    @Test
    void setPre() {
        LRUNode node = new LRUNode("A");
        LRUNode temp = new LRUNode("B");
        node.setPre(temp);
        assertEquals(temp, node.getPre());
    }

    @Test
    void getPre() {
        LRUNode node = new LRUNode("A");
        LRUNode temp = new LRUNode("B");
        node.setPre(temp);
        assertEquals(temp, node.getPre());
    }

    @Test
    void setNext() {
        LRUNode node = new LRUNode("A");
        LRUNode temp = new LRUNode("B");
        node.setNext(temp);
        assertEquals(temp, node.getNext());
    }

    @Test
    void getNext() {
        LRUNode node = new LRUNode("A");
        LRUNode temp = new LRUNode("B");
        node.setNext(temp);
        assertEquals(temp, node.getNext());
    }
}