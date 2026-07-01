package translation.model;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import translation.util.Constants;
import translation.util.DateUtil;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 前缀树
 *
 * @author: hello
 * @since: 2023/2/21
 */
public class ThePreTree {

    private final static Logger log = LoggerFactory.getLogger(ThePreTree.class);
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock read = lock.readLock();
    private ReentrantReadWriteLock.WriteLock write = lock.writeLock();

    public class PreNode {
        boolean isCrash;//碰撞...
        Character c;
        boolean isWord;//是否为单词 _ 有重复校验的;
        Map<Character, PreNode> next = new HashMap<>();
        @Getter
        @Setter
        UrlMapBean sense; //存放数据的,可以是个Object; 后期可以改用List<UrlMapBean>

        //但是没必要
        public PreNode() {
        }

        public PreNode(Character c) {
            this.c = c;
        }

        public String toString() {
            String str = null;
            str = String.format("c:%s;isWord:%s;isCrash:%s;sense:%s", c, isWord, isCrash, sense);
            return str;
        }
    }

    PreNode root;
    //    @Getter
    AtomicInteger wordSize = new AtomicInteger();
    //    @Getter
    AtomicInteger charSize = new AtomicInteger();

    public ThePreTree() {
        root = new PreNode();
    }

    public String toString() {
        String str = null;
        str = MessageFormat.format("wordSize:{0};charSize:{1}", wordSize, charSize);
        return str;
    }

    /**
     * 存储短链接-长链接的映射;
     */
    public String addOrSet(String word, UrlMapBean urlMapBean) {
        if (word == null || word.length() == 0) {
            log.error("传入的word数据非法:" + word);
            return null;
        }
        PreNode currNode = root, subNode = null;
        Character currC = null;
        try {
            write.lock();
            for (int i = 0; i < word.length(); i++) {
                currC = word.charAt(i);
                if (!currNode.next.containsKey(currC)) {
                    subNode = new PreNode(currC);
                    currNode.next.put(currC, subNode);
                    this.charSize.getAndIncrement();
                }
                currNode = currNode.next.get(currC);
            }
            if (!currNode.isWord) {
                currNode.isWord = true;
                this.wordSize.incrementAndGet();
            } else {
                currNode.isCrash = true;//表明发生过碰撞;测试环节会验证
                return Constants.REPET;
            }
            currNode.setSense(urlMapBean);
        } catch (Exception e) {
//            e.printStackTrace();
            log.error("新增短链接发生异常:{};{};", e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        } finally {
            write.unlock();
        }
        return word;
    }

    /**
     * 根据短链接得到长链接 O(w)复杂度; w 字段长度本次 作业为6个长度的短字节
     * 所以与O(1)一样
     */
    public UrlMapBean get(String shortUrl) {
        PreNode preNode = getPreNode(shortUrl);
        return preNode == null ? null : preNode.getSense();
    }

    /**
     * 根据短链接得到长链接 O(w)复杂度; w 字段长度本次 作业为6个长度的短字节
     * 所以与O(1)一样
     */
    public PreNode getPreNode(String word) {
        if (word == null || word.length() == 0) {
            log.error("传入的word数据非法:" + word);
            return null;
        }
        PreNode currNode = root, subNode = null;
        Character currC = null;
        for (int i = 0; i < word.length(); i++) {
            currC = word.charAt(i);
            if (!currNode.next.containsKey(currC)) {
                break;
            }
            currNode = currNode.next.get(currC);
        }
        if (currNode.isWord) {
            return currNode;
        } else {
            return null;
        }
    }

    /**
     * 排查是否存在同样的key
     */
    public boolean contains(String word) {
        boolean flag = false;
        PreNode currNode = root;
        Character currC = null;
        try {
            read.lock();
            for (int i = 0; i < word.length(); i++) {
                currC = word.charAt(i);
                if (currNode.next.containsKey(currC)) {
                    currNode = currNode.next.get(currC);
                } else {
                    return flag;
                }
            }
        } finally {
            read.unlock();
        }
        flag = currNode.isWord;
        return flag;
    }

    /**
     * 为了测试用例而存在的 层序遍历  ; 测试 碰撞 看是否存在覆盖之前的长链接
     */
    public void printLevel() throws Exception {
        LinkedList<PreNode> queue = new LinkedList<>();
        queue.add(root);
        PreNode curr = null;
        while (!queue.isEmpty()) {
            curr = queue.removeFirst();
            if (curr.getSense() != null && curr.isCrash) {
                String msg = null;
                msg = String.format("发生碰撞:对应的信息为:%s;", curr.getSense());
                log.error(msg);
                throw new Exception(msg);
            }
            if (curr.next != null && curr.next.size() != 0) {
                curr.next.forEach((k, v) -> queue.add(v));
            }
        }
    }

    /**
     * 清理过期的 key
     */
    public void clearCache(int cacheDay) {
        LinkedList<PreNode> queue = new LinkedList<>();
        queue.add(root);
        PreNode curr = null;
        while (!queue.isEmpty()) {
            curr = queue.removeFirst();
            if (curr.isWord && DateUtil.betweenNow(curr.getSense().getCreatIime(), TimeUnit.DAYS) > cacheDay) {
                curr.setSense(null);
                this.wordSize.decrementAndGet();
                curr.isWord = false;
            }
            if (curr.next != null && curr.next.size() != 0) {
                curr.next.forEach((k, v) -> queue.add(v));
            }
        }
    }
}