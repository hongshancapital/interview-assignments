package com.jinblog.shorturl.entry;

/**
 * LRU node对象，用于LRU回收算法
 */
public class LRUNode extends AbstractNode {
    private LRUNode pre, next;
    public LRUNode(String data) {
        super(data);
    }

    public void setPre(LRUNode pre) {
        this.pre = pre;
    }

    public LRUNode getPre() {
        return pre;
    }

    public void setNext(LRUNode next) {
        this.next = next;
    }

    public LRUNode getNext() {
        return next;
    }
}
