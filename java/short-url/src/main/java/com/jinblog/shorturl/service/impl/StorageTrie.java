package com.jinblog.shorturl.service.impl;

import com.jinblog.shorturl.service.Storage;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class StorageTrie implements Storage {

    class Trie {
        /**
         * 子节点
         */
        private Map<Character, Trie> children = new HashMap<>();
        /**
         * 记录当前节点子节点数量
         */
        private AtomicInteger count = new AtomicInteger();
        /**
         * 节点对应的原始字符串
         */
        private String str = null;

        public Trie insert(String str, String targetStr) {
            int size = str.length();
            short index;
            Trie node = this, temp;
            node.count.incrementAndGet();
            for (int i = 0; i < size; i++) {
                temp = node.children.get(str.charAt(i));
                if (temp == null) {
                    synchronized (node) {
                        temp = node.children.get(str.charAt(i));
                        if (temp == null) {
                            temp = new Trie();
                            node.children.put(str.charAt(i), temp);
                        }
                    }
                }
                temp.count.incrementAndGet();
                node = temp;
            }
            node.str = targetStr;
            return node;
        }

        public Trie find(String str) {
            int size = str.length();
            Trie node = this, temp;
            for (int i = 0; i < size; i++) {
                temp = node.children.get(str.charAt(i));
                if (temp == null) {
                    return null;
                }
                node = temp;
            }
            return node;
        }

        public void delete(String str) {
            int size = str.length();
            Trie node = this, temp;
            for (int i = 0; i < size; i++) {
                temp = node.children.get(str.charAt(i));
                if (temp == null) {
                    break;
                }
                synchronized (node) {
                    if (temp.count.decrementAndGet() < 1) {
                        node.children.remove(str.charAt(i));
                        break;
                    }
                }
                node = temp;
            }
        }
    }

    private final Trie trie = new Trie();

    @Override
    public void save(String url, String shortUrl) {
        trie.insert(shortUrl, url);
    }

    @Override
    public String find(String url) {
        Trie t = trie.find(url);
        return t == null ? null : t.str;
    }

    @Override
    public void delete(String key) {
        trie.delete(key);
    }
}
