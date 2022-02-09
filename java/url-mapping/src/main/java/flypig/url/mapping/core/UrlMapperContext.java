package flypig.url.mapping.core;

import flypig.url.mapping.util.Long2StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Thread Safe.
 */
public class UrlMapperContext {

    private static final int SHORT_URL_SIZE_BYTES = 20;

    private static final int SHORT_URL_SIZE_PER_ARRAY = 1 << SHORT_URL_SIZE_BYTES;

    private Node urlTreeRoot = new Node();

    private List<Node[]> shortUrls = new ArrayList<>();

    private AtomicLong lastID = new AtomicLong(0);

    //读写锁，实现线程安全
    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

    private ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();

    private static UrlMapperContext INSTANCE = new UrlMapperContext();

    private UrlMapperContext() {}

    public static UrlMapperContext getInstance() {
        return INSTANCE;
    }
    /**
     * find long url for short url.
     * @param shortUrl
     * @return
     */
    public String short2long(String shortUrl) {
        long id = Long2StringUtil.string2long(shortUrl);
        Node node = null;
        readLock.lock();
        try {
            if (id > lastID.get()) {
                return null;
            }
            int firstIndex = (int) (id >> 30);
            int secondIdx = (int) (id & (SHORT_URL_SIZE_PER_ARRAY - 1));

            node = shortUrls.get(firstIndex)[secondIdx];

        } finally {
            readLock.unlock();
        }

        StringBuilder sb = new StringBuilder("");
        while (node != urlTreeRoot) {
            sb.append(node.c);
            node = node.parent;
        }
        return sb.reverse().toString();
    }

    /**
     * fetch short url for long url, find an old one or insert a new one.
     * @param longUrl
     * @return
     */
    public String long2short(String longUrl) {
        Node p = null;
        readLock.lock();
        try {
            p = findNode(longUrl);
        } finally {
            readLock.unlock();
        }

        if (p == null || p.shortUrlId == null) {
            writeLock.lock();
            try {
                p = p != null ? p : insertUrlToTree(longUrl);
                p.shortUrlId = lastID.incrementAndGet();
                bindShortUrlWithNode(p);
            } finally {
                writeLock.unlock();
            }
        }

        return Long2StringUtil.long2String(p.shortUrlId);
    }

    /**
     * insert longurl into urltree.
     *
     * @param longUrl
     * @return
     */
    private Node insertUrlToTree(String longUrl) {
        Node p = urlTreeRoot;
        int i = 0;
        outer:
        for (; i < longUrl.length(); i++) {
            for (Node child : p.children) {
                if (child.c == longUrl.charAt(i)) {
                    p = child;
                    continue outer;
                }
            }
            break;
        }
        for (; i < longUrl.length(); i++) {
            Node nodeToAdd = new Node();
            nodeToAdd.c = longUrl.charAt(i);
            nodeToAdd.parent = p;
            p.children.add(nodeToAdd);
            p = nodeToAdd;
        }
        return p;
    }

    /**
     * find target node in urltree for long url.
     * @param longUrl
     * @return
     */
    private Node findNode(String longUrl) {
        Node p = urlTreeRoot;
        outer:
        for (int i = 0; i < longUrl.length(); i++) {
            for (Node child : p.children) {
                if (child.c == longUrl.charAt(i)) {
                    p = child;
                    continue outer;
                }
            }
            return null;
        }
        return p;
    }

    private void bindShortUrlWithNode(Node p) {
        int firstIndex = (int) (p.shortUrlId >> SHORT_URL_SIZE_BYTES);
        int secondIdx = (int) (p.shortUrlId & (SHORT_URL_SIZE_PER_ARRAY - 1));
        if (firstIndex >= shortUrls.size()) {
            shortUrls.add(new Node[SHORT_URL_SIZE_PER_ARRAY]);
        }
        shortUrls.get(firstIndex)[secondIdx] = p;
    }

    private class Node {

        private Character c;

        private List<Node> children = new ArrayList<>();

        private Node parent;

        private Long shortUrlId;
    }
}
